package com.mucheng.mucute.relay

import com.mucheng.mucute.relay.handler.MinecraftRelayPacketHandler
import com.mucheng.mucute.relay.handler.SessionCloseHandler
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption
import org.cloudburstmc.protocol.bedrock.BedrockClientSession
import org.cloudburstmc.protocol.bedrock.BedrockPeer
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockChannelInitializer
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.UnknownPacket
import java.net.InetSocketAddress
import kotlin.random.Random


class MinecraftRelaySession internal constructor(
    peer: BedrockPeer,
    subClientId: Int,
    val minecraftRelay: MinecraftRelay,
    val connectAddress: InetSocketAddress
) {

    val receiveClientPacketSession = ReceiveClientPacketSession(peer, subClientId)

    var receiveServerPacketSession: ReceiveServerPacketSession? = null
        private set(value) {
            value?.let {
                it.codec = receiveClientPacketSession.codec
                it.peer.codecHelper.blockDefinitions = receiveClientPacketSession.peer.codecHelper.blockDefinitions
                it.peer.codecHelper.itemDefinitions = receiveClientPacketSession.peer.codecHelper.itemDefinitions
                sendToServerPacketQueue.forEach { pair ->
                    if (pair.second) {
                        it.sendPacketImmediately(pair.first)
                    } else {
                        it.sendPacket(pair.first)
                    }
                }
                sendToServerPacketQueue.clear()
            }
            field = value
        }

    val listeners: MutableList<MinecraftRelayPacketHandler> = ArrayList()

    private val sendToServerPacketQueue: MutableList<Pair<BedrockPacket, Boolean>> = ArrayList()

    internal fun newClient(onReceiveServerPacketSessionCreated: (ReceiveServerPacketSession) -> Unit) {
        Bootstrap()
            .group(NioEventLoopGroup())
            .channelFactory(RakChannelFactory.client(NioDatagramChannel::class.java))
            .option(RakChannelOption.RAK_PROTOCOL_VERSION, receiveClientPacketSession.codec.raknetProtocolVersion)
            .option(RakChannelOption.RAK_GUID, Random.nextLong())
            .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator(true))
            .handler(object : BedrockChannelInitializer<ReceiveServerPacketSession>() {
                override fun createSession0(peer: BedrockPeer, subClientId: Int): ReceiveServerPacketSession {
                    return ReceiveServerPacketSession(peer, subClientId)
                }

                override fun initSession(receiveServerPacketSession: ReceiveServerPacketSession) {
                    this@MinecraftRelaySession.receiveServerPacketSession = receiveServerPacketSession
                    onReceiveServerPacketSessionCreated(receiveServerPacketSession)
                }
            })
            .connect(connectAddress)
            .syncUninterruptibly()
    }

    fun sendToClient(packet: BedrockPacket) {
        receiveClientPacketSession.sendPacket(packet)
    }

    fun sendToClientImmediately(packet: BedrockPacket) {
        receiveClientPacketSession.sendPacketImmediately(packet)
    }

    fun sendToServer(packet: BedrockPacket) {
        if (receiveServerPacketSession != null) {
            receiveServerPacketSession!!.sendPacket(packet)
        } else {
            sendToServerPacketQueue.add(packet to false)
        }
    }

    fun sendToServerImmediately(packet: BedrockPacket) {
        if (receiveServerPacketSession != null) {
            receiveServerPacketSession!!.sendPacketImmediately(packet)
        } else {
            sendToServerPacketQueue.add(packet to true)
        }
    }

    inner class ReceiveClientPacketSession(peer: BedrockPeer, subClientId: Int) :
        BedrockServerSession(peer, subClientId) {

        init {
            packetHandler = SessionCloseHandler {
                println("ReceiveClientPacketSession disconnect: $it")
                try {
                    receiveServerPacketSession?.disconnect()
                } catch (_: Exception) {
                }

                listeners.forEach { listener ->
                    try {
                        listener.onDisconnect(it)
                    } catch (_: Throwable) {
                    }
                }
            }
        }

        override fun onPacket(wrapper: BedrockPacketWrapper) {
            val packet = wrapper.packet

            listeners.forEach { listener ->
                try {
                    if (listener.onReceivedFromClient(packet)) {
                        return
                    }
                } catch (e: Throwable) {
                    println("ReceiveClientPacketSession error: ${e.stackTraceToString()}")
                }
            }

            val buffer = wrapper.packetBuffer
                .retainedSlice()
                .skipBytes(wrapper.headerLength)

            val sendPacket = UnknownPacket()
            sendPacket.payload = buffer
            sendPacket.packetId = wrapper.packetId
            sendToServer(sendPacket)
        }

    }

    inner class ReceiveServerPacketSession(peer: BedrockPeer, subClientId: Int) :
        BedrockClientSession(peer, subClientId) {

        init {
            packetHandler = SessionCloseHandler {
                println("ReceiveServerPacketSession disconnect: $it")
                try {
                    receiveClientPacketSession.disconnect()
                } catch (_: Exception) {
                }

                listeners.forEach { listener ->
                    try {
                        listener.onDisconnect(it)
                    } catch (_: Throwable) {
                    }
                }
            }
        }

        override fun onPacket(wrapper: BedrockPacketWrapper) {
            val packet = wrapper.packet

            listeners.forEach { listener ->
                try {
                    if (listener.onReceivedFromServer(packet)) {
                        return
                    }
                } catch (e: Throwable) {
                    println("ReceiveServerPacketSession error: ${e.stackTraceToString()}")
                }
            }

            val buffer = wrapper.packetBuffer
                .retainedSlice()
                .skipBytes(wrapper.headerLength)

            val sendPacket = UnknownPacket()
            sendPacket.payload = buffer
            sendPacket.packetId = wrapper.packetId
            sendToClient(sendPacket)
        }

    }

}
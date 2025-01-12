package com.mucheng.mucute.relay

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.PooledByteBufAllocator
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import net.raphimc.minecraftauth.step.bedrock.session.StepFullBedrockSession
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption
import org.cloudburstmc.netty.handler.codec.raknet.server.RakServerRateLimiter
import org.cloudburstmc.protocol.bedrock.BedrockPeer
import org.cloudburstmc.protocol.bedrock.BedrockPong
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper
import org.cloudburstmc.protocol.bedrock.codec.v729.serializer.InventoryContentSerializer_v729
import org.cloudburstmc.protocol.bedrock.codec.v729.serializer.InventorySlotSerializer_v729
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766
import org.cloudburstmc.protocol.bedrock.data.EncodingSettings
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer
import org.cloudburstmc.protocol.bedrock.packet.InventoryContentPacket
import org.cloudburstmc.protocol.bedrock.packet.InventorySlotPacket
import java.net.InetSocketAddress
import kotlin.random.Random

class MinecraftRelay(
    val authSession: StepFullBedrockSession.FullBedrockSession? = null,
    private val advertisement: BedrockPong = DefaultAdvertisement,
    private val minecraftRelaySessionCreated: (MinecraftRelaySession) -> Unit
) {

    @Suppress("MemberVisibilityCanBePrivate")
    companion object {

        val DefaultCodecHelper: BedrockCodecHelper by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Bedrock_v766.CODEC.createHelper().apply {
                encodingSettings = EncodingSettings.UNLIMITED
            }
        }

        val DefaultCodec: BedrockCodec by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Bedrock_v766.CODEC.toBuilder()
                .helper { DefaultCodecHelper }
                .updateSerializer(InventoryContentPacket::class.java, InventoryContentSerializer_v729.INSTANCE)
                .updateSerializer(InventorySlotPacket::class.java, InventorySlotSerializer_v729.INSTANCE)
                .build()
        }

        val DefaultAdvertisement: BedrockPong by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            BedrockPong()
                .edition("MCPE")
                .gameType("Survival")
                .version(DefaultCodec.minecraftVersion)
                .protocolVersion(DefaultCodec.protocolVersion)
                .motd("MuCuteRelay")
                .playerCount(0)
                .maximumPlayerCount(20)
                .subMotd("Join QQ Group: 542992134")
                .nintendoLimited(false)
        }

    }

    private var channelFuture: ChannelFuture? = null

    private var minecraftRelaySession: MinecraftRelaySession? = null

    @Suppress("MemberVisibilityCanBePrivate")
    val isRunning: Boolean
        get() = channelFuture != null

    fun start(
        minecraftRelayAddress: InetSocketAddress = InetSocketAddress("0.0.0.0", 19132),
        connectAddress: InetSocketAddress = InetSocketAddress("geo.hivebedrock.network", 19132)
    ) {
        if (isRunning) {
            return
        }

        advertisement
            .ipv4Port(minecraftRelayAddress.port)
            .ipv6Port(minecraftRelayAddress.port)

        ServerBootstrap()
            .group(NioEventLoopGroup())
            .channelFactory(RakChannelFactory.server(NioDatagramChannel::class.java))
            .option(RakChannelOption.RAK_ADVERTISEMENT, advertisement.toByteBuf())
            .option(RakChannelOption.RAK_GUID, Random.nextLong())
            .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator(true))
            .childHandler(object : BedrockServerInitializer() {

                override fun createSession0(peer: BedrockPeer, subClientId: Int): BedrockServerSession {
                    return MinecraftRelaySession(peer, subClientId, this@MinecraftRelay, connectAddress)
                        .also { minecraftRelaySession = it }
                        .also { minecraftRelaySessionCreated(it) }
                        .receiveClientPacketSession
                }

                override fun initSession(session: BedrockServerSession) {

                }

            })
            .bind(minecraftRelayAddress)
            .syncUninterruptibly()
            .also {
                it.channel().pipeline().remove(RakServerRateLimiter.NAME)
                channelFuture = it
            }
    }

    fun stop() {
        if (!isRunning) {
            return
        }

        channelFuture?.channel()?.also {
            it.close().syncUninterruptibly()
            it.parent().close().syncUninterruptibly()
        }
        channelFuture = null
    }

}
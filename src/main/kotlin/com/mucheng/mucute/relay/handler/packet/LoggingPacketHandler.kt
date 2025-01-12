package com.mucheng.mucute.relay.handler.packet

import com.mucheng.mucute.relay.handler.MinecraftRelayPacketHandler
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket

@Suppress("MemberVisibilityCanBePrivate")
open class LoggingPacketHandler : MinecraftRelayPacketHandler {

    var isEnabled = true

    override fun onReceivedFromClient(packet: BedrockPacket): Boolean {
        if (isEnabled) {
            println("onReceivedFromClient: $packet")
        }
        return false
    }

    override fun onReceivedFromServer(packet: BedrockPacket): Boolean {
        if (isEnabled) {
            println("onReceivedFromServer: $packet")
        }
        return false
    }

}
package com.mucheng.mucute.relay.listener

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket

@Suppress("MemberVisibilityCanBePrivate")
open class LoggingPacketListener : MuCuteRelayPacketListener {

    var isEnabled = true

    override fun beforeClientBound(packet: BedrockPacket): Boolean {
        if (isEnabled) {
            println("onReceivedFromClient: $packet")
        }
        return false
    }

    override fun beforeServerBound(packet: BedrockPacket): Boolean {
        if (isEnabled) {
            println("onReceivedFromServer: $packet")
        }
        return false
    }

}
package com.mucheng.mucute.relay.handler

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket

interface MinecraftRelayPacketHandler {

    fun onReceivedFromClient(packet: BedrockPacket): Boolean {
        return false
    }

    fun onReceivedFromServer(packet: BedrockPacket): Boolean {
        return false
    }

    fun onDisconnect(reason: String) {

    }

}
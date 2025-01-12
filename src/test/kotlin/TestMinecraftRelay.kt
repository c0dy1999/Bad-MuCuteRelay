import com.mucheng.mucute.relay.MinecraftRelay
import com.mucheng.mucute.relay.handler.MinecraftRelayPacketHandler
import com.mucheng.mucute.relay.handler.packet.NecessaryPacketHandler
import com.mucheng.mucute.relay.util.getAuthSession
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket
import org.cloudburstmc.protocol.bedrock.packet.TextPacket
import java.net.InetSocketAddress
import java.nio.file.Paths

fun main() {
    val file = Paths.get(".").resolve("auth.json")
    val authSession = getAuthSession(file.toFile())
    val address = InetSocketAddress("geo.hivebedrock.network", 19132)
    MinecraftRelay(
        authSession = authSession
    ) {

        it.listeners.add(object : MinecraftRelayPacketHandler {
            override fun onReceivedFromClient(packet: BedrockPacket): Boolean {
                if (packet is PlayerAuthInputPacket && packet.tick % 10 == 0L) {
                    it.sendToClient(TextPacket().apply {
                        type = TextPacket.Type.TIP
                        isNeedsTranslation = false
                        message = "Hello, world!"
                        sourceName = ""
                        xuid = ""
                    })
                }
                return super.onReceivedFromClient(packet)
            }
        })
        // it.listeners.add(LoggingPacketHandler())
        it.listeners.add(NecessaryPacketHandler(it))
    }.start(
        connectAddress = address
    )
    println("Server started on ${address}")

    while (true) {

    }
}
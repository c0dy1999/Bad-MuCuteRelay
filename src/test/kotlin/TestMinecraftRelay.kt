import com.mucheng.mucute.relay.definition.Definitions
import com.mucheng.mucute.relay.listener.NecessaryPacketListener
import com.mucheng.mucute.relay.util.captureMuCuteRelay
import com.mucheng.mucute.relay.util.fetchAuthSession
import java.net.InetSocketAddress
import java.nio.file.Paths

fun main() {
    val path = Paths.get(".").resolve("auth.json")
    val authSession = fetchAuthSession(path.toFile())
    val remoteAddress = InetSocketAddress("play.cubecraft.net", 19132)

    Definitions.loadBlockPalette()
    captureMuCuteRelay(
        remoteAddress = remoteAddress,
        authSession = authSession
    ) {
        listeners.add(NecessaryPacketListener(this))
    }

    println("MuCuteRelay started on ${remoteAddress.hostName}:${remoteAddress.port}")
}
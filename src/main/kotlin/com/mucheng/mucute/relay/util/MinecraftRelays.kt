package com.mucheng.mucute.relay.util

import com.google.gson.JsonParser
import com.mucheng.mucute.relay.MuCuteRelay
import com.mucheng.mucute.relay.MuCuteRelaySession
import net.lenni0451.commons.httpclient.HttpClient
import net.raphimc.minecraftauth.MinecraftAuth
import net.raphimc.minecraftauth.step.bedrock.session.StepFullBedrockSession
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode.MsaDeviceCode
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode.MsaDeviceCodeCallback
import org.cloudburstmc.protocol.bedrock.BedrockPong
import java.io.File
import java.net.InetSocketAddress

fun fetchAuthSession(
    file: File,
    saveAuthFile: Boolean = true,
    codeCallback: MsaDeviceCodeCallback = MsaDeviceCodeCallback { msaDeviceCode: MsaDeviceCode ->
        println("Go to " + msaDeviceCode.verificationUri)
        println("Enter code " + msaDeviceCode.userCode)
    }
): StepFullBedrockSession.FullBedrockSession {
    val client: HttpClient = MinecraftAuth.createHttpClient()
    var authSession = if (!file.exists() || file.isDirectory) {
        MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.getFromInput(client, codeCallback)
    } else {
        MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.fromJson(JsonParser.parseString(file.readText()).getAsJsonObject())
    }

    authSession = MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.refresh(client, authSession)

    if (saveAuthFile) {
        file.writeText(MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.toJson(authSession).toString())
    }

    return authSession
}

fun captureMuCuteRelay(
    remoteAddress: InetSocketAddress,
    localAddress: InetSocketAddress = InetSocketAddress("0.0.0.0", 19132),
    authSession: StepFullBedrockSession.FullBedrockSession? = null,
    advertisement: BedrockPong = MuCuteRelay.DefaultAdvertisement,
    beforeCapture: () -> Unit = {},
    onSessionCreated: MuCuteRelaySession.() -> Unit
): MuCuteRelay {
    val muCuteRelay = MuCuteRelay(
        localAddress = localAddress,
        authSession = authSession,
        advertisement = advertisement
    )
    beforeCapture()
    muCuteRelay.capture(
        remoteAddress = remoteAddress,
        onSessionCreated = onSessionCreated
    )
    return muCuteRelay
}
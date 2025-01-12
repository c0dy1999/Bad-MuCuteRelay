package com.mucheng.mucute.relay.util

import com.google.gson.JsonParser
import net.lenni0451.commons.httpclient.HttpClient
import net.raphimc.minecraftauth.MinecraftAuth
import net.raphimc.minecraftauth.step.bedrock.session.StepFullBedrockSession
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode.MsaDeviceCode
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode.MsaDeviceCodeCallback
import java.io.File

fun getAuthSession(
    file: File,
    saveAuthDetails: Boolean = true,
    codeCallback: MsaDeviceCodeCallback = MsaDeviceCodeCallback { msaDeviceCode: MsaDeviceCode ->
        println("Go to " + msaDeviceCode.verificationUri)
        println("Enter code " + msaDeviceCode.userCode)
    }
): StepFullBedrockSession.FullBedrockSession {
    val client: HttpClient = MinecraftAuth.createHttpClient()
    var account: StepFullBedrockSession.FullBedrockSession

    if (!file.exists() || !saveAuthDetails) {
        account = MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.getFromInput(client, codeCallback)

        if (saveAuthDetails) {
            file.bufferedWriter().use {
                val jsonObject = MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.toJson(account)
                it.write(jsonObject.toString())
            }
        }

        return account
    }

    val accountString = file.readText()
    account = MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.fromJson(JsonParser.parseString(accountString).getAsJsonObject())
    account = MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.refresh(client, account)

    file.bufferedWriter().use {
        val jsonObject = MinecraftAuth.BEDROCK_DEVICE_CODE_LOGIN.toJson(account)
        it.write(jsonObject.toString())
    }

    return account
}
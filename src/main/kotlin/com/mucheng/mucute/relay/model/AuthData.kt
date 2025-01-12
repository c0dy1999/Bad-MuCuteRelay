package com.mucheng.mucute.relay.model

import java.util.*

@Suppress("SpellCheckingInspection")
data class AuthData(
    val displayName: String,
    val identity: UUID,
    val xuid: String
)
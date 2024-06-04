package cn.hanasaka.events.model

import kotlinx.serialization.Serializable

@Serializable
data class Identify(
    val op: Int,
    val body: IdentifyBody
)

@Serializable
data class IdentifyBody(
    val token: String? = null,
    val sequence: Int? = null
)

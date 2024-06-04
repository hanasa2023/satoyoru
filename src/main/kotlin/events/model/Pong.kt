package cn.hanasaka.events.model

import kotlinx.serialization.Serializable

@Serializable
data class Pong(
    val op: Int,
    val body: String? = null
)

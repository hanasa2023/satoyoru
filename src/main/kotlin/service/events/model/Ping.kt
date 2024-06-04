package cn.hanasaka.service.events.model

import kotlinx.serialization.Serializable

@Serializable
data class Ping(
    val op: Int,
    val body: String? = null
)

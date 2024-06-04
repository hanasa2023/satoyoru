package cn.hanasaka.service.channel.model

import kotlinx.serialization.Serializable


@Serializable
data class Channel(
    val id: String,
    val type: Int,
    val name: String? = null,
    val parent_id: String? = null
)

package cn.hanasaka.channel.model

import kotlinx.serialization.Serializable


@Serializable
data class Channel(
    val id: String,
    val type: Int,
    val name: String? = null,
    val parent_id: String? = null
)

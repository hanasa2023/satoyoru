package cn.hanasaka.service.guild.model

import kotlinx.serialization.Serializable

@Serializable
data class Guild(
    val id: String,
    val name: String? = null,
    val avatar: String? = null
)

@Serializable
data class GuildData(
    val data: List<Guild>
)

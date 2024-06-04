package cn.hanasaka.service.guildMember.model

import cn.hanasaka.service.user.model.User
import kotlinx.serialization.Serializable

@Serializable
data class GuildMember(
    val user: User? = null,
    val nick: String? = null,
    val avatar: String? = null,
    val joined_at: Int? = null
)

@Serializable
data class GuildMemberData(
    val data: List<GuildMember>
)

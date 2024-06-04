package cn.hanasaka.service.guildMember.model

import kotlinx.serialization.Serializable


@Serializable
data class GetGuildMemberBody(
    val guild_id: String,
    val user_id: String
)

@Serializable
data class KickMemberBody(
    val guild_id: String,
    val user_id: String,
    val permanent: Boolean? = null
)

@Serializable
data class MuteGuildMemberBody(
    val guild_id: String,
    val user_id: String,
    val duration: Int,
    val comment: String? = null
)

@Serializable
data class ApproveGuildMemberBody(
    val message_id: String,
    val approve: Boolean,
    val comment: String? = null
)
package cn.hanasaka.service.message.model

import cn.hanasaka.service.channel.model.Channel
import cn.hanasaka.service.guild.model.Guild
import cn.hanasaka.service.guildMember.model.GuildMember
import cn.hanasaka.service.user.model.User
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    val content: String,
    val channel: Channel? = null,
    val guild: Guild? = null,
    val member: GuildMember? = null,
    val user: User? = null,
    val created_at: Int? = null,
    val updated_at: Int? = null
)

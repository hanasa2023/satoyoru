package cn.hanasaka.message.model

import cn.hanasaka.channel.model.Channel
import cn.hanasaka.guild.model.Guild
import cn.hanasaka.guildMember.model.GuildMember
import cn.hanasaka.user.model.User
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

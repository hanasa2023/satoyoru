package cn.hanasaka.events.model

import cn.hanasaka.channel.model.Channel
import cn.hanasaka.guild.model.Guild
import cn.hanasaka.guildMember.model.GuildMember
import cn.hanasaka.login.model.Login
import cn.hanasaka.message.model.Message
import cn.hanasaka.user.model.User
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val op: Int,
    val body: EventBody? = null
)

@Serializable
data class EventBody(
    val id: Int,
    val type: String,
    val platform: String,
    val self_id: String,
    val timestamp: Long,
    val channel: Channel? = null,
    val guild: Guild,
    val login: Login? = null,
    val member: GuildMember? = null,
    val message: Message? = null,
    val operator: User? = null,
    val user: User? = null
)

package cn.hanasaka.service.events.model

import cn.hanasaka.service.channel.model.Channel
import cn.hanasaka.service.guild.model.Guild
import cn.hanasaka.service.guildMember.model.GuildMember
import cn.hanasaka.service.login.model.Login
import cn.hanasaka.service.message.model.Message
import cn.hanasaka.service.user.model.User
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
    val guild: Guild? = null,
    val login: Login? = null,
    val member: GuildMember? = null,
    val message: Message? = null,
    val operator: User? = null,
    val user: User? = null
)

package cn.hanasaka.guildMember

import cn.hanasaka.BASE_URL
import cn.hanasaka.guild.model.SingleGuildIDBody
import cn.hanasaka.guildMember.model.GuildMember
import cn.hanasaka.guildMember.model.GuildMemberData
import cn.hanasaka.guildMember.model.ApproveGuildMemberBody
import cn.hanasaka.guildMember.model.GetGuildMemberBody
import cn.hanasaka.guildMember.model.KickMemberBody
import cn.hanasaka.guildMember.model.MuteGuildMemberBody
import cn.hanasaka.utils.SatoyoruClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object GuildMember {
    private val _client = SatoyoruClient.client

    /**
     * 获取群组成员
     * @param guildID 群组ID
     * @param userID 用户ID
     */
    suspend fun getGuildMember(
        guildID: String,
        userID: String
    ): GuildMember {
        try {
            val res = _client.post("$BASE_URL/guild.member.get") {
                contentType(ContentType.Application.Json)
                setBody(
                    GetGuildMemberBody(
                        guild_id = guildID,
                        user_id = userID
                    )
                )
            }
            val resBody = res.body<GuildMember>()
            return resBody
        } catch (e: Exception) {
            println(e.message)
            return GuildMember()
        }
    }

    /**
     * 获取群组成员列表
     * @param guildID 群组ID
     */
    suspend fun getGuildMemberList(guildID: String): GuildMemberData {
        try {
            val res = _client.post("$BASE_URL/guild.member.list") {
                contentType(ContentType.Application.Json)
                setBody(SingleGuildIDBody(guildID))
            }
            val resBody = res.body<GuildMemberData>()
            return resBody
        } catch (e: Exception) {
            println(e.message)
            return GuildMemberData(emptyList())
        }
    }

    /**
     * 踢出群组成员，成功返回true
     * @param guildID 群组ID
     * @param userID 用户ID
     * @param permanent 是否永久踢出
     */
    suspend fun kickGuildMember(
        guildID: String,
        userID: String,
        permanent: Boolean?
    ): Boolean {
        try {
            _client.post("$BASE_URL/guild.member.kick") {
                contentType(ContentType.Application.Json)
                setBody(
                    KickMemberBody(
                        guild_id = guildID,
                        user_id = userID,
                        permanent = permanent
                    )
                )
            }
            return true
        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }

    /**
     * 群组用户禁言，成功返回true
     * @param guildID 群组ID
     * @param userID 用户ID
     * @param duration 禁言时长，当设置为0时表示解除禁言
     * @param comment 禁言群组的说明，目前会忽略此字段
     */
    suspend fun muteGuildMember(
        guildID: String,
        userID: String,
        duration: Int,
        comment: String? = "nothing"
    ): Boolean {
        try {
            _client.post("$BASE_URL/guild.member.mute") {
                contentType(ContentType.Application.Json)
                setBody(
                    MuteGuildMemberBody(
                        guild_id = guildID,
                        user_id = userID,
                        duration = duration,
                        comment = comment
                    )
                )
            }
            return true
        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }

    /**
     * 处理加群请求，成功返回true
     * @param messageID 请求ID
     * @param approve 是否通过请求
     * @param comment 备注信息
     */
    suspend fun approveGuildMember(
        messageID: String,
        approve: Boolean,
        comment: String? = "nothing"
    ): Boolean {
        try {
            _client.post("$BASE_URL/guild.member.approve") {
                contentType(ContentType.Application.Json)
                setBody(
                    ApproveGuildMemberBody(
                        message_id = messageID,
                        approve = approve,
                        comment = comment
                    )
                )
            }
            return true
        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }
}
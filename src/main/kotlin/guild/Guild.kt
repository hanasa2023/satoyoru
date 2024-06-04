package cn.hanasaka.guild

import cn.hanasaka.BASE_URL
import cn.hanasaka.guild.model.Guild
import cn.hanasaka.guild.model.GuildData
import cn.hanasaka.guild.model.ApproveBody
import cn.hanasaka.guild.model.SingleGuildIDBody
import cn.hanasaka.utils.SatoyoruClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object Guild {
    private val _client = SatoyoruClient.client

    /**
     * 获取群组信息
     * @param guildID 群号
     */
    suspend fun getGuildInfo(
        guildID: String
    ): Guild {
        try {
            val response = _client.post("$BASE_URL/guild.get") {
                contentType(ContentType.Application.Json)
                setBody(SingleGuildIDBody(guildID))
            }
            val responseBody = response.body<Guild>()
            return responseBody
        } catch (e: Exception) {
            println(e.message)
        }
        return Guild("")
    }

    /**
     * 获取群组列表
     */
    suspend fun getGuildList(): GuildData {
        try {
            val response = _client.post("$BASE_URL/guild.list")
            val responseBody = response.body<GuildData>()
            return responseBody
        } catch (e: Exception) {
            println(e.message)
        }
        return GuildData(emptyList())
    }

    /**
     *  处理来自群组的邀请，成功返回true
     *  @param messageID 请求ID
     *  @param approve 是否通过请求
     *  @param comment 备注信息
     */
    suspend fun approveGuild(
        messageID: String,
        approve: Boolean,
        comment: String
    ): Boolean {
        try {
            _client.post("$BASE_URL/guild.approve") {
                contentType(ContentType.Application.Json)
                setBody(
                    ApproveBody(
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

    /**
     * 删除群组，成功返回true
     * @param guildID 要删除群组的ID
     */
    suspend fun removeGuild(guildID: String): Boolean {
        try {
            _client.post("$BASE_URL/unsafe.guild.remove") {
                contentType(ContentType.Application.Json)
                setBody(SingleGuildIDBody(guildID))
            }
            return true
        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }
}
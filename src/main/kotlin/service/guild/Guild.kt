package cn.hanasaka.service.guild

import cn.hanasaka.utils.BASE_URL
import cn.hanasaka.service.guild.model.Guild
import cn.hanasaka.service.guild.model.GuildData
import cn.hanasaka.service.guild.model.ApproveBody
import cn.hanasaka.service.guild.model.SingleGuildIDBody
import cn.hanasaka.utils.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


/**
 * 获取群组信息
 * @param guildID 群号
 */
suspend fun HttpClient.getGuildInfo(
    guildID: String
): Guild {
    try {
        val response = use {
            post("$BASE_URL/guild.get") {
                contentType(ContentType.Application.Json)
                setBody(SingleGuildIDBody(guildID))

            }
        }
        val responseBody = response.body<Guild>()
        return responseBody
    } catch (e: Exception) {
        Log.error(e)
    }
    return Guild("")
}

/**
 * 获取群组列表
 */
suspend fun HttpClient.getGuildList(): GuildData {
    try {
        val response = use {
            post("$BASE_URL/guild.list")
        }
        val responseBody = response.body<GuildData>()
        return responseBody
    } catch (e: Exception) {
        Log.error(e)
    }
    return GuildData(emptyList())
}

/**
 *  处理来自群组的邀请，成功返回true
 *  @param messageID 请求ID
 *  @param approve 是否通过请求
 *  @param comment 备注信息
 */
suspend fun HttpClient.approveGuild(
    messageID: String,
    approve: Boolean,
    comment: String
): Boolean {
    try {
        use {
            post("$BASE_URL/guild.approve") {
                contentType(ContentType.Application.Json)
                setBody(
                    ApproveBody(
                        message_id = messageID,
                        approve = approve,
                        comment = comment
                    )
                )

            }
        }
        return true
    } catch (e: Exception) {
        Log.error(e)
        return false
    }
}

/**
 * 删除群组，成功返回true
 * @param guildID 要删除群组的ID
 */
suspend fun HttpClient.removeGuild(guildID: String): Boolean {
    try {
        use {
            post("$BASE_URL/unsafe.guild.remove") {
                contentType(ContentType.Application.Json)
                setBody(SingleGuildIDBody(guildID))
            }
        }
        return true
    } catch (e: Exception) {
        Log.error(e)
        return false
    }
}
package cn.hanasaka.service.message

import cn.hanasaka.utils.BASE_URL
import cn.hanasaka.service.message.model.CreateMessageBody
import cn.hanasaka.service.message.model.Message
import cn.hanasaka.utils.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


/**
 * 发送消息
 * @param channelID 目标频道ID
 * @param content 要发送的内容，格式为Satori消息元素字符串
 */
suspend fun HttpClient.createMessage(
    channelID: String,
    content: String
): List<Message> {
    try {
        val res = post("$BASE_URL/message.create") {
            contentType(ContentType.Application.Json)
            setBody(
                CreateMessageBody(
                    channel_id = channelID,
                    content = content
                )
            )
        }
        val resBody = res.body<List<Message>>()
        return resBody
    } catch (e: Exception) {
        Log.error(e)
        return emptyList()
    }
}
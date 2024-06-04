package cn.hanasaka.message

import cn.hanasaka.BASE_URL
import cn.hanasaka.message.model.CreateMessageBody
import cn.hanasaka.message.model.Message
import cn.hanasaka.utils.SatoyoruClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object Message {
    private val _client = SatoyoruClient.client

    /**
     * 发送消息
     * @param channelID 目标频道ID
     * @param connect 要发送的内容，格式为Satori消息元素字符串
     */
    suspend fun createMessage(
        channelID: String,
        content: String
    ): List<Message> {
        try {
            val res = _client.post("$BASE_URL/message.create") {
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
            println(e.message)
            return emptyList()
        }
    }
}
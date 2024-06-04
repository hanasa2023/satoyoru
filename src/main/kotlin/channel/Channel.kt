package cn.hanasaka.channel

import cn.hanasaka.BASE_URL
import cn.hanasaka.channel.model.Channel
import cn.hanasaka.channel.model.CreateChannelBody
import cn.hanasaka.channel.model.MuteChannelBody
import cn.hanasaka.utils.SatoyoruClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

//TODO:处理异常
object Channel {
    private val _client = SatoyoruClient.client

    /**
     * 创建私聊频道
     * @param userID 想要创建的用户qq号
     * @author hanasaki
     */
    suspend fun createPrivateChannel(userID: String): Channel {
        try {
            val response = _client.post("$BASE_URL/user.channel.create") {
                contentType(ContentType.Application.Json)
                setBody(CreateChannelBody(userID))
            }
            val responseBody: Channel = response.body<Channel>()
            return responseBody
        } catch (e: Exception) {
            println(e.message)
            return Channel("", -1)
        }
    }

    /**
     * 频道开启/关闭全体禁言，正常开启/关闭返回true，否则返回false
     * @param channelID 要全体禁言的频道
     * @param enable 开启/关闭禁言，若值为true则开启，false则关闭
     * @author hanasaki
     */
    suspend fun muteChannel(
        channelID: String,
        enable: Boolean
    ): Boolean {
        try {
            _client.post("$BASE_URL/unsafe.channel.mute") {
                contentType(ContentType.Application.Json)
                setBody(
                    MuteChannelBody(
                        channel_id = channelID,
                        enable = enable
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
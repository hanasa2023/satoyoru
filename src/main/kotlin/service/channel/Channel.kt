package cn.hanasaka.service.channel

import cn.hanasaka.utils.BASE_URL
import cn.hanasaka.service.channel.model.Channel
import cn.hanasaka.service.channel.model.CreateChannelBody
import cn.hanasaka.service.channel.model.MuteChannelBody
import cn.hanasaka.utils.Log
import com.github.ajalt.mordant.terminal.Terminal
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * 创建私聊频道
 * @param userID 想要创建的用户qq号
 * @author hanasaki
 */
suspend fun HttpClient.createPrivateChannel(
    userID: String,
): Channel {
    try {
        val res = use {
            post("$BASE_URL/user.channel.create") {
                contentType(ContentType.Application.Json)
                setBody(CreateChannelBody(userID))
            }
        }
        val resBody: Channel = res.body<Channel>()
        return resBody
    } catch (e: Exception) {
        Log.error(e)
        return Channel("", -1)
    }
}

/**
 * 频道开启/关闭全体禁言，正常开启/关闭返回true，否则返回false
 * @param channelID 要全体禁言的频道
 * @param enable 开启/关闭禁言，若值为true则开启，false则关闭
 * @author hanasaki
 */
suspend fun HttpClient.muteChannel(
    channelID: String,
    enable: Boolean
): Boolean {
    try {
        use {
            post("$BASE_URL/unsafe.channel.mute") {
                contentType(ContentType.Application.Json)
                setBody(
                    MuteChannelBody(
                        channel_id = channelID,
                        enable = enable
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

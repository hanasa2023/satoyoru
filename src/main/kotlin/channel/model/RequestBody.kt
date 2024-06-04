package cn.hanasaka.channel.model

import kotlinx.serialization.Serializable

/**
 * @param user_id 要私聊的用户
 */
@Serializable
data class CreateChannelBody(
    val user_id: String
)

/**
 * @param channel_id 要禁言的频道
 * @param enable 开启/关闭禁言
 */
@Serializable
data class MuteChannelBody(
    val channel_id: String,
    val enable: Boolean
)


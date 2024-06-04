package cn.hanasaka.service.guild.model

import kotlinx.serialization.Serializable

/**
 * @param guild_id 群组ID
 */
@Serializable
data class SingleGuildIDBody(
    val guild_id: String
)

/**
 * @param message_id 请求ID
 * @param approve 是否通过请求
 * @param comment 备注信息
 */
@Serializable
data class ApproveBody(
    val message_id: String,
    val approve: Boolean,
    val comment: String
)
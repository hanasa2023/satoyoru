package cn.hanasaka.message.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateMessageBody(
    val channel_id: String,
    val content: String
)
package cn.hanasaka.service.user.model

import kotlinx.serialization.Serializable

@Serializable
data class SingleUserIDBody(
    val user_id: String
)

package cn.hanasaka.user.model

import kotlinx.serialization.Serializable

@Serializable
data class SingleUserIDBody(
    val user_id: String
)

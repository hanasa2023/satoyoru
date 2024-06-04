package cn.hanasaka.user.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String? = null,
    val nick: String? = null,
    val avatar: String? = null,
    val is_bot: Boolean? = null
)

@Serializable
data class UserData(
    val data: List<User>? = null
)

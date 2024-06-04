package cn.hanasaka.login.model

import cn.hanasaka.user.model.User
import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val user: User? = null,
    val self_id: String? = null,
    val platform: String? = null,
    val status: Int
)

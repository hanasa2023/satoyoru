package cn.hanasaka.events.model

import cn.hanasaka.login.model.Login
import kotlinx.serialization.Serializable

@Serializable
data class Ready(
    val op: Int,
    val body: Logins? = null
)

@Serializable
data class Logins(
    val logins: List<Login>
)
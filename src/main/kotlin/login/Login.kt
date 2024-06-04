package cn.hanasaka.login

import cn.hanasaka.BASE_URL
import cn.hanasaka.login.model.Login
import cn.hanasaka.utils.SatoyoruClient
import io.ktor.client.call.*
import io.ktor.client.request.*

object Login {
    private val _client = SatoyoruClient.client

    /**
     * 获取登录信息
     */
    suspend fun getLoginInfo(): Login {
        try {
            val res = _client.post("$BASE_URL/login.get")
            val resBody = res.body<Login>()
            return resBody
        } catch (e: Exception) {
            println(e.message)
            return Login(status = -1)
        }
    }
}
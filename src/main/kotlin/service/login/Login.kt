package cn.hanasaka.service.login

import cn.hanasaka.utils.BASE_URL
import cn.hanasaka.service.login.model.Login
import cn.hanasaka.utils.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


/**
 * 获取登录信息
 */
suspend fun HttpClient.getLoginInfo(): Login {
    try {
        val res = post("$BASE_URL/login.get")
        val resBody = res.body<Login>()
        return resBody
    } catch (e: Exception) {
        Log.error(e)
        return Login(status = -1)
    }
}
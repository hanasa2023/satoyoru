package cn.hanasaka.service.user

import cn.hanasaka.utils.BASE_URL
import cn.hanasaka.service.guild.model.ApproveBody
import cn.hanasaka.service.user.model.SingleUserIDBody
import cn.hanasaka.service.user.model.UserData
import cn.hanasaka.utils.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


/**
 * 获取好友列表
 */
suspend fun HttpClient.getFriendList(): List<UserData> {
    try {
        val res = post("$BASE_URL/friend.list")
        val resBody = res.body<List<UserData>>()
        return resBody
    } catch (e: Exception) {
        Log.error(e)
        return emptyList()
    }
}

/**
 * 处理好友申请，成功返回true
 * @param messageID 请求ID
 * @param approve 是否通过请求
 * @param comment 备注信息
 */
suspend fun HttpClient.approveFriend(
    messageID: String,
    approve: Boolean,
    comment: String
): Boolean {
    try {
        post("/friend.approve") {
            contentType(ContentType.Application.Json)
            setBody(
                ApproveBody(
                    message_id = messageID,
                    approve = approve,
                    comment = comment
                )
            )
        }
        return true
    } catch (e: Exception) {
        Log.error(e)
        return false
    }
}

/**
 * 删除好友，成功返回true
 * @param userID 删除好友的ID
 */
suspend fun HttpClient.removeFriend(userID: String): Boolean {
    try {
        post("$BASE_URL/unsafe.friend.remove") {
            contentType(ContentType.Application.Json)
            setBody(SingleUserIDBody(userID))
        }
        return true
    } catch (e: Exception) {
        Log.error(e)
        return false
    }
}
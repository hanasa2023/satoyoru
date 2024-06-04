package cn.hanasaka.user

import cn.hanasaka.BASE_URL
import cn.hanasaka.guild.model.ApproveBody
import cn.hanasaka.user.model.SingleUserIDBody
import cn.hanasaka.user.model.UserData
import cn.hanasaka.utils.SatoyoruClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object User {
    private val _client = SatoyoruClient.client

    /**
     * 获取好友列表
     */
    suspend fun getFriendList(): List<UserData> {
        try {
            val res = _client.post("$BASE_URL/friend.list")
            val resBody = res.body<List<UserData>>()
            return resBody
        } catch (e: Exception) {
            println(e.message)
            return emptyList()
        }
    }

    /**
     * 处理好友申请，成功返回true
     * @param messageID 请求ID
     * @param approve 是否通过请求
     * @param comment 备注信息
     */
    suspend fun approveFriend(
        messageID: String,
        approve: Boolean,
        comment: String
    ): Boolean {
        try {
            _client.post("/friend.approve") {
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
            println(e.message)
            return false
        }
    }

    /**
     * 删除好友，成功返回true
     * @param userID 删除好友的ID
     */
    suspend fun removeFriend(userID: String): Boolean {
        try {
            _client.post("$BASE_URL/unsafe.friend.remove") {
                contentType(ContentType.Application.Json)
                setBody(SingleUserIDBody(userID))
            }
            return true
        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }
}
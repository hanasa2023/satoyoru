package cn.hanasaka

import cn.hanasaka.service.events.listening
import cn.hanasaka.service.message.createMessage
import cn.hanasaka.utils.satoyoru


fun main() {
    val satoyoru = satoyoru {
        listening { api, event ->
            val channelID = event.body?.channel?.id
            val message = event.body?.message?.content
            if (channelID == "private:2019266396" && message == "test1")
                api.createMessage(channelID, "test")
        }
    }
    satoyoru.close()
}

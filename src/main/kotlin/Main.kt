package cn.hanasaka

import cn.hanasaka.service.events.listening
import cn.hanasaka.service.message.createMessage
import cn.hanasaka.utils.MessageUtil
import cn.hanasaka.utils.satoyoru
import java.io.File


fun main() {
    val satoyoru = satoyoru {
        listening { api, event ->
            val channelID = event.body?.channel?.id
            val message = event.body?.message?.content?.let { MessageUtil.parse(it) }
            message?.let {
                if (message.isAtMe && message.primaryText == "echo")
                    channelID?.let { channelId ->
                        api.createMessage(
                            channelId,
                            MessageUtil.create {
                                event.body.user?.id?.let { at(id = it) }
                                +"快来玩pjsk(x)"
                                face(id = "353")
                                img(File("src/main/resources/images/mzk.png").toURI().toURL().toString())
                            }
                        )
                    }
            }
        }
    }
    satoyoru.close()
}

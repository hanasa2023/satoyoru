# Satoyoru

[![GitHub](https://img.shields.io/github/license/hanasa2023/satoyoru.svg?logo=github)](https://github.com/hanasa2023/satoyoru?tab=MIT-1-ov-file)

基于[kotlin](https://kotlinlang.org)和cc的bot开发框架

## 📝 示例

接收到```@me```且消息为```echo```时，回复
![回复消息](https://github.com/hanasa2023/satoyoru/blob/master/doc/resources/images/createdMessage.jpg)

```kotlin
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
```

## ✅ TODO

- [x] 消息处理
    - [x] 实现消息解析功能
    - [x] 实现消息构造功能
    - [x] ~~提供人性化消息信息获取接口~~
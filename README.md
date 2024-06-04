# Satoyoru

<hr>
[![GitHub](https://img.shields.io/github/license/hanasa2023/satoyoru?style=for-the-badge)](https://github.com/hanasa2023/satoyoru?tab=MIT-1-ov-file)

基于[kotlin](https://kotlinlang.org)和cc的bot开发框架

<hr>

## 📝 示例

接收到```user_id```为```1145141919810```的用户发来的消息```echo```时，回复```test```

```kotlin
fun main() {
    val satoyoru = satoyoru {
        listening { api, event ->
            val channelID = event.body?.channel?.id
            val message = event.body?.message?.content
            if (channelID == "private:1145141919" && message == "echo")
                api.createMessage(channelID, "test")
        }
    }
    satoyoru.close()
}
```

## ✅ TODO

- [ ] 消息处理
    - [ ] 实现消息解析功能
    - [ ] 实现消息构造功能
package cn.hanasaka.service.events

import cn.hanasaka.utils.TOKEN
import cn.hanasaka.service.events.model.*
import cn.hanasaka.utils.BotInfo
import cn.hanasaka.utils.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

/**
 * 开启事件监听
 */
fun HttpClient.listening(block: suspend HttpClient.(api: HttpClient, message: Event) -> Unit) {
    val timer = Timer("sendIdentify", true)

    val api = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(TOKEN, TOKEN)
                }
            }
        }
    }

    runBlocking {
        webSocket("ws://localhost:5500/v1/events") {
            sendSerialized(Identify(3, IdentifyBody(token = TOKEN)))
            timer.scheduleAtFixedRate(0, 10_000) {
                launch {
                    sendSerialized(Ping(1))
                }
            }
            while (true) {
                var event = Event(-1)

                when (val frame = incoming.receive()) {
                    is Frame.Text -> {
                        val res = frame.readText()
                        val resJsonElement = Json.parseToJsonElement(res)
                        val resJsonObject = resJsonElement.jsonObject
                        when (resJsonObject["op"]?.jsonPrimitive?.int) {
                            0 -> {
                                event = Json.decodeFromJsonElement<Event>(resJsonElement)
//                                println(resJsonElement)
                                Log.normal(event)
                            }

                            2 -> {
//                                Log.normal("心跳回复")
                            }

                            4 -> {
                                val ready = Json.decodeFromJsonElement<Ready>(resJsonElement)
                                ready.body?.logins?.forEach { login ->
                                    login.self_id?.let { BotInfo.ids.add(it) }
                                }
                                Log.normal(ready)
                            }
                        }
                    }

                    else -> {}
                }
                if (event.op == 0) block(api, event)
            }
        }
    }
}
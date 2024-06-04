package cn.hanasaka.events

import cn.hanasaka.TOKEN
import cn.hanasaka.events.model.*
import cn.hanasaka.events.model.Event
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

object Events {
    fun startSatoyoru(client: HttpClient, timer: Timer) {
        runBlocking {
            client.webSocket("ws://localhost:5500/v1/events") {
                sendSerialized(Identify(3, IdentifyBody(token = TOKEN)))
                timer.scheduleAtFixedRate(0, 10_000) {
                    launch {
                        sendSerialized(Ping(1))
                    }
                }
                while (true) {
                    when (val frame = incoming.receive()) {
                        is Frame.Text -> {
                            val res = frame.readText()
                            val resJsonElement = Json.parseToJsonElement(res)
                            val resJsonObject = resJsonElement.jsonObject
                            when (resJsonObject["op"]?.jsonPrimitive?.int) {
                                0 -> {
                                    val event = Json.decodeFromJsonElement<Event>(resJsonElement)
                                    println(event)
                                    println(resJsonElement)
                                }

                                2 -> {
                                    println("心跳回复")
                                }

                                4 -> {
                                    val ready = Json.decodeFromJsonElement<Ready>(resJsonElement)
                                    println(ready)
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }

    }
}
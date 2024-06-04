package cn.hanasaka

import cn.hanasaka.events.Events
import cn.hanasaka.utils.SatoyoruClient
import java.util.Timer


fun main() {
    val timer = Timer("sendIdentify", true)
    val satoyoru = SatoyoruClient.eventClient
    Events.startSatoyoru(client = satoyoru, timer = timer)
    timer.cancel()
    satoyoru.close()
}

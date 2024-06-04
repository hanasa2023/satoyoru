package cn.hanasaka.utils

import cn.hanasaka.service.events.model.Event
import cn.hanasaka.service.events.model.Ready
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Log {
    private val t = Terminal()

    fun normal(event: Any) {
        when (event) {
            is Event -> {
                val date = LocalDateTime.now()
                    .format(
                        DateTimeFormatter.ofPattern("MM-dd HH:mm:ss")
                    )
                t.println(
                    TextColors.gray("[$date]")
                            + TextColors.brightGreen("[N] ")
                            + TextColors.blue(
                        (event.body?.channel?.name ?: "undefined")
                                + "(${event.body?.channel?.id ?: "undefined"})"
                    )
                            + "-"
                            + TextColors.cyan(
                        (event.body?.member?.nick ?: "undefined")
                                + "(${event.body?.user?.id ?: "undefined"})"
                    )
                            + TextColors.gray(": ")
                            + event.body?.message?.content
                )
            }

            is Ready -> {
                val date = LocalDateTime.now()
                    .format(
                        DateTimeFormatter.ofPattern("MM-dd HH:mm:ss")
                    )
                val logins = event.body?.logins
                for (login in logins ?: emptyList()) {
                    t.println(
                        TextColors.gray("[$date]")
                                + TextColors.brightGreen("[N] ")
                                + login.self_id + " 登录成功!!!!! Bot 开始运行"
                    )
                }
            }

            is String -> {
                val date = LocalDateTime.now()
                    .format(
                        DateTimeFormatter.ofPattern("MM-dd HH:mm:ss")
                    )
                t.println(
                    TextColors.gray("[$date]")
                            + TextColors.brightGreen("[N] ")
                            + event
                )

            }
        }
    }

    fun error(e: Exception) {
        val date = LocalDateTime.now()
            .format(
                DateTimeFormatter.ofPattern("MM-dd HH:mm:ss")
            )
        t.println(
            TextColors.gray("[$date]")
                    + TextColors.brightRed("[E] ")
                    + e.message
        )
    }
}
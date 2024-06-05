package cn.hanasaka.utils


class MessageInfo(
    val textList: List<String>? = null,
    val at: List<At>? = null,
    val face: List<Face>? = null,
    val img: List<Img>? = null,
    val audio: List<Audio>? = null,
    val video: List<Video>? = null,
    val file: List<File>? = null,
    val message: List<Message>? = null,
    val quote: List<Quote>? = null,
    val author: List<Author>? = null,
    val marketFace: List<MarketFace>? = null
) {
    private val hasAt: Boolean = at != null

    val hasFace: Boolean = face != null

    val hasImg: Boolean = img != null

    val hasAudio: Boolean = audio != null

    val hasVideo: Boolean = video != null

    val hasFile: Boolean = file != null

    val hasMessage: Boolean = message != null

    //一般认为quote必然带有author元素
    val hasQuote: Boolean = quote != null

    val hasAuthor: Boolean = author != null

    val hasMarketFace: Boolean = marketFace != null

    val isAtMe: Boolean = if (hasAt) {
        var atMe = false
        at!!.forEach {
            if (BotInfo.ids.any { id -> id == it.id }) {
                atMe = true
                return@forEach
            }
        }
        atMe
    } else false

    val text = textList?.joinToString("")

    val primaryText = textList?.lastOrNull()
}

object MessageUtil {
    //TODO:提供链接(Markdown)
    fun parse(message: String): MessageInfo {
        val textList = mutableListOf<String>()
        val at = mutableListOf<At>()
        val face = mutableListOf<Face>()
        val img = mutableListOf<Img>()
        val audio = mutableListOf<Audio>()
        val video = mutableListOf<Video>()
        val file = mutableListOf<File>()
        val msg = mutableListOf<Message>()
        val quote = mutableListOf<Quote>()
        val author = mutableListOf<Author>()
        val marketFace = mutableListOf<MarketFace>()

        val document = org.jsoup.Jsoup.parse(
            message,
            "",
            org.jsoup.parser.Parser.xmlParser()
        )

        document.allElements.forEach { element ->
            if (element.tagName() != "#root") {
                when (element.tagName()) {
                    "at" -> {
                        val itAt = At(
                            id = element.attr("id"),
                            name = element.attr("name"),
                            role = element.attr("role"),
                            type = element.attr("type")
                        )
                        at.add(itAt)
                        textList.add("@${itAt.name}(${itAt.id}) ")
                    }

                    "chronocat:face" -> {
                        val itFace = Face(
                            id = element.attr("id"),
                            name = element.attr("name"),
                            platform = element.attr("platform")
                        )
                        face.add(itFace)
                        textList.add(itFace.name ?: "[表情] ")
                    }

                    "img" -> {
                        val itImg = Img(
                            src = element.attr("src"),
                            title = element.attr("title"),
                            cache = element.attr("cache").toBoolean(),
                            timeout = element.attr("timeout"),
                            width = element.attr("width").let { width ->
                                when {
                                    width.isNotEmpty() -> width.toInt()
                                    else -> null
                                }
                            },
                            height = element.attr("width").let { height ->
                                when {
                                    height.isNotEmpty() -> height.toInt()
                                    else -> null
                                }
                            }
                        )
                        img.add(itImg)
                        textList.add("[图片] ")
                    }

                    "audio" -> {
                        val itAudio = Audio(
                            src = element.attr("src"),
                            title = element.attr("title"),
                            cache = element.attr("cache").toBoolean(),
                            timeout = element.attr("timeout"),
                            duration = element.attr("duration").let {
                                when (it.isNotEmpty()) {
                                    true -> it.toInt()
                                    else -> null
                                }
                            },
                            poster = element.attr("poster")
                        )
                        audio.add(itAudio)
                        textList.add("[语音] ")
                    }

                    "video" -> {
                        val itVideo = Video(
                            src = element.attr("src"),
                            title = element.attr("title"),
                            cache = element.attr("cache").toBoolean(),
                            timeout = element.attr("timeout"),
                            width = element.attr("width").let {
                                when (it.isNotEmpty()) {
                                    true -> it.toInt()
                                    else -> null
                                }
                            },
                            height = element.attr("height").let {
                                when (it.isNotEmpty()) {
                                    true -> it.toInt()
                                    else -> null
                                }
                            },
                            duration = element.attr("duration").let {
                                when (it.isNotEmpty()) {
                                    true -> it.toInt()
                                    else -> null
                                }
                            },
                            poster = element.attr("poster")
                        )
                        video.add(itVideo)
                        textList.add("[视频] ")
                    }

                    "file" -> {
                        val itFile = File(
                            src = element.attr("src"),
                            title = element.attr("title"),
                            cache = element.attr("cache").toBoolean(),
                            timeout = element.attr("timeout"),
                            poster = element.attr("poster")
                        )
                        file.add(itFile)
                        textList.add("[文件] ")
                    }

                    "message" -> {
                        val itMsg = Message(
                            id = element.attr("content"),
                            forward = element.attr("type").toBoolean()
                        )
                        msg.add(itMsg)
                        textList.add("[消息] ")
                    }

                    "quote" -> {
                        val itQuote = Quote(
                            element.attr("chronocat:seq"),
                        )
                        val authorElement = element.getElementsByTag("author")
                        quote.add(itQuote)
                        textList.add(
                            com.github.ajalt.mordant.rendering.TextColors.gray("[回复${itQuote.seq}]")
//                        Markdown(
//                            markdown = "[[回复${quote.seq}]](${authorElement.attr("avatar")})",
//                            hyperlinks = true
//                        ).toString()
                        )
                    }

                    "author" -> {
                        val itAuthor = Author(
                            element.attr("id"),
                            element.attr("name"),
                            element.attr("avatar")
                        )
                        author.add(itAuthor)
                        textList.add("@${itAuthor.name}(${itAuthor.id}) ")
                    }

//                商城表情解析似乎没必要
                    "chronocat:marketface" -> {
                        val itMarketFace = MarketFace(
                            element.attr("id"),
                            element.attr("name"),
                            element.attr("avatar")
                        )
                        marketFace.add(itMarketFace)
//                    text.add("[商城表情]")
                    }

                }
//            it.childNodes().forEach { childNode ->
//                if (childNode.nameIs("#text") && childNode.toString().trim().isNotEmpty())
//                    println(
//                        childNode.toString().trim()
//                    )
//            }
            }
        }
        document.childNodes().forEach {
            if (it.nameIs("#text") && it.toString().trim().isNotEmpty())
                textList.add(it.toString().trim())
        }
        return MessageInfo(
            textList = textList,
            at = at,
            face = face,
            img = img,
            audio = audio,
            video = video,
            file = file,
            message = msg,
            quote = quote,
            author = author,
            marketFace = marketFace
        )
    }

    fun create(init: CreatedMessage.() -> Unit): String {
        val msg = CreatedMessage()
        msg.init()
        return msg.getChildren()
    }
}

interface Element {
    fun render(builder: StringBuilder, indent: String)
}

class TextElement(private val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }

    override fun toString(): String {
        return text
    }
}

abstract class Tag(private val name: String) : Element {
    val children = mutableListOf<Element>()
    val attributes = hashMapOf<String, String>()

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name")
        for ((attr, value) in attributes) {
            builder.append(" $attr=\"$value\"")
        }
        if (children.isEmpty()) {
            builder.append("/>\n")
        } else {
            builder.append(">\n")
            for (child in children) {
                child.render(builder, "$indent  ")
            }
            builder.append("$indent</$name>\n")
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }

    fun getChildren(): String {
        return children.joinToString("")
    }
}

abstract class TagWithText(name: String) : Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class CreatedMessage : CreatedMessageTag("")

abstract class CreatedMessageTag(name: String) : TagWithText(name) {

    /**
     * 创建at元素
     * @param id 目标用户的id
     * @param name 目标用户的名称
     * @param role 目标角色
     * @param type 特殊操作，all表示@全体成员，here表示@在线成员
     */
    fun at(
        id: String = "",
        name: String = "",
        role: String = "",
        type: String = "",
        init: AtElement.() -> Unit = {}
    ) {
        val at = initTag(AtElement(), init)
        at.id = id
        at.name = name
        at.role = role
        at.type = type
    }

    //TODO:应为输入表情名称更人性化，需要制作一份表情名称->表情id表
    /**
     * 创建表情元素
     * @param id 表情的id
     * @param platform 表情显示的平台
     */
    fun face(
        id: String,
        platform: String = "chronocat",
        init: FaceElement.() -> Unit = {}
    ) {
        val face = initTag(FaceElement(), init)
        face.id = id
        face.platform = platform
    }

    /**
     * 创建图片元素
     * @param src 图片的URL，建议使用本地URL，网络URL会有不可避免的延迟
     */
    fun img(
        src: String,
        init: ImgElement.() -> Unit = {}
    ) {
        val img = initTag(ImgElement(), init)
        img.src = src
    }

    /**
     * 创建音频元素
     * @param src 音频的URL，建议使用本地URL，网络URL会有不可避免的延迟
     */
    fun audio(
        src: String,
        init: AudioElement.() -> Unit = {}
    ) {
        val audio = initTag(AudioElement(), init)
        audio.src = src
    }

    /**
     * 创建视频元素
     * @param src 视频的URL，建议使用本地URL，网络URL会有不可避免的延迟
     */
    fun video(
        src: String,
        init: VideoElement.() -> Unit = {}
    ) {
        val video = initTag(VideoElement(), init)
        video.src = src
    }

    /**
     * 创建文件元素
     * @param src 文件的URL
     */
    fun file(
        src: String,
        init: FileElement.() -> Unit = {}
    ) {
        val file = initTag(FileElement(), init)
        file.src = src
    }

    /**
     * 创建消息元素
     * @param id 消息的id
     * @param forward 是否为转发消息
     */
    fun message(
        id: String = "",
        forward: Boolean = false,
        init: MessageElement.() -> Unit = {}
    ) {
        val message = initTag(MessageElement(), init)
        message.id = id
        message.forward = forward
    }

    /**
     * 创建引用元素，cc文档没写，seq未知，摆了QAQ
     */
    fun quote(seq: String, init: QuoteElement.() -> Unit) {
        val quote = initTag(QuoteElement(), init)
        quote.seq = seq
    }
}

class AtElement : CreatedMessageTag("at") {
    var id: String
        get() = attributes["id"]!!
        set(value) {
            attributes["id"] = value
        }
    var name: String
        get() = attributes["name"]!!
        set(value) {
            attributes["name"] = value
        }
    var role: String
        get() = attributes["role"]!!
        set(value) {
            attributes["role"] = value
        }
    var type: String
        get() = attributes["type"]!!
        set(value) {
            attributes["type"] = value
        }
}

class FaceElement : CreatedMessageTag("chronocat:face") {
    var id: String
        get() = attributes["id"]!!
        set(value) {
            attributes["id"] = value
        }

    var platform: String
        get() = attributes["platform"]!!
        set(value) {
            attributes["platform"] = value
        }
}

class ImgElement : CreatedMessageTag("img") {
    var src: String
        get() = attributes["src"]!!
        set(value) {
            attributes["src"] = value
        }
}

class AudioElement : CreatedMessageTag("audio") {
    var src: String
        get() = attributes["src"]!!
        set(value) {
            attributes["src"] = value
        }
}

class VideoElement : CreatedMessageTag("vedio") {
    var src: String
        get() = attributes["src"]!!
        set(value) {
            attributes["src"] = value
        }
}

class FileElement : CreatedMessageTag("file") {
    var src: String
        get() = attributes["src"]!!
        set(value) {
            attributes["src"] = value
        }
}

class MessageElement : CreatedMessageTag("message") {
    var id: String
        get() = attributes["id"]!!
        set(value) {
            attributes["id"] = value
        }

    var forward: Boolean
        get() = attributes["forward"].toBoolean()
        set(value) {
            attributes["forward"] = value.toString()
        }
}

class QuoteElement : CreatedMessageTag("quote") {
    var seq: String
        get() = attributes["chronocat:seq"]!!
        set(value) {
            attributes["chronocat:seq"] = value
        }
}


//不建议创建
class MarketFaceElement : CreatedMessageTag("chronocat:marketface") {
    var `tab-id`: String
        get() = attributes["tab-id"]!!
        set(value) {
            attributes["tab-id"] = value
        }

    var `face-id`: String
        get() = attributes["face-id"]!!
        set(value) {
            attributes["face-id"] = value
        }

    var key: String
        get() = attributes["key"]!!
        set(value) {
            attributes["key"] = value
        }
}

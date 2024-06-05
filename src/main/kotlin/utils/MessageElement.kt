package cn.hanasaka.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Text(
    val text: String
)

@Serializable
data class At(
    val id: String? = null,
    val name: String? = null,
    val role: String? = null,
    val type: String? = null
)

@Serializable
@SerialName("chronocat:face")
data class Face(
    val id: String,
    val name: String? = null,
    val platform: String? = null
)

@Serializable
data class Img(
    val src: String,
    val title: String? = null,
    val cache: Boolean? = null,
    val timeout: String? = null,
    val width: Int? = null,
    val height: Int? = null
)

@Serializable
data class Audio(
    val src: String,
    val title: String? = null,
    val cache: Boolean? = null,
    val timeout: String? = null,
    val duration: Int? = null,
    val poster: String? = null
)

@Serializable
data class Video(
    val src: String,
    val title: String? = null,
    val cache: Boolean? = null,
    val timeout: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val duration: Int? = null,
    val poster: String? = null
)

@Serializable
data class File(
    val src: String,
    val title: String? = null,
    val cache: Boolean? = null,
    val timeout: String? = null,
    val poster: String? = null
)

@Serializable
@SerialName("message")
data class Message(
    val id: String? = null,
    val forward: Boolean? = null
)

@Serializable
data class Quote(
    @SerialName("chronocat:seq")
    val seq: String? = null,
)

@Serializable
data class Author(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null
)

@Serializable
@SerialName("chronocat:marketface")
data class MarketFace(
    @SerialName("tab-id")
    val tabId: String? = null,
    @SerialName("face-id")
    val faceId: String? = null,
    val key: String? = null
)
package com.kidnapsteal.coincommunity.data.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

fun Conversation.generate(sender: User, receiver: User, message: String, type: ConversationType): Conversation {
    this.id = UUID.randomUUID().toString()
    this.message = message
    this.type = type
    this.timeStamp = System.currentTimeMillis()
    this.sender = sender
    this.senderId = sender.userId
    this.receiver = receiver
    this.receiverId = receiver.userId
    this.status = ConversationStatus.SENDING
    this.viewType = ConversationViewType.LEFT

    return this
}

enum class ConversationStatus(val value: Int) {
    FAILED(0),
    SENDING(1),
    SENT(2),
    READ(3)
}

enum class ConversationViewType(val value: Int) {
    LEFT(0),
    RIGHT(1)
}

sealed class ConversationType {
    data class Video(val url: String = "",
                     val duration: Int = 0,
                     val size: Long = 0L) : ConversationType()

    data class Image(val url: String = "") : ConversationType()

    data class Message(val message: String = "") : ConversationType()
}

class ConversationConverter {

    @TypeConverter
    fun conversationStatusToInt(conversationStatus: ConversationStatus): Int = conversationStatus.value

    @TypeConverter
    fun convertIntToConversationStatus(value: Int): ConversationStatus = ConversationStatus.values()[value]

    @TypeConverter
    fun conversationViewTypeToInt(conversationViewType: ConversationViewType): Int = conversationViewType.value

    @TypeConverter
    fun convertIntToConversationViewType(value: Int): ConversationViewType = ConversationViewType.values()[value]

    @TypeConverter
    fun conversationTypeToString(type: ConversationType): String {
        return when (type) {
            is ConversationType.Video -> {
                val value = 0
                val duration = type.duration
                val size = type.size
                val url = type.url
                String.format("%d,%d,%d,%s", value, duration, size, url)
            }
            is ConversationType.Image -> {
                val value = 1
                String.format("%d,%d,%d,%s", value, 0, 0, type.url)
            }
            is ConversationType.Message -> {
                String.format("%d,%d,%d,%s", 2, 0, 0, type.message)
            }
        }
    }

    @TypeConverter
    fun conversationTypeToString(data: String): ConversationType {
        val raw = data.split(",")

        return when (raw[0].toInt()) {
            0 -> {
                ConversationType.Video(
                        url = raw[3],
                        duration = raw[1].toInt(),
                        size = raw[2].toLong())
            }
            1 -> {
                ConversationType.Image(raw[3])
            }
            else -> {
                ConversationType.Message(raw[3])
            }
        }
    }

    @TypeConverter
    fun userToString(user: User): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun stringToUser(data: String): User {
        return Gson().fromJson(data, User::class.java)
    }
}
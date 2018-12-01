package com.kidnapsteal.coincommunity.data.local.entity

import com.google.gson.Gson

data class ConversationFirebase(
        var id: String = "",
        var message: String? = null,
        var type: FirebaseConversationType? = null,
        var timeStamp: Long = 0L,
        var sender: User? = null,
        var senderId: String? = null,
        var receiver: User? = null,
        var receiverId: String? = null,
        var status: Int? = null,
        var viewType: Int? = null
)

data class FirebaseConversationType(val id: Int = -1,
                                    val data: String? = "")

fun Conversation.toFirebase(): ConversationFirebase {
    val data = Gson().toJson(type)
    val type = when (this.type) {
        is ConversationType.Video -> {
            FirebaseConversationType(0, data)
        }
        is ConversationType.Image -> {
            FirebaseConversationType(1, data)
        }
        else -> {
            FirebaseConversationType(2, data)
        }
    }
    val conversationFirebase = ConversationFirebase(
            id = this.id,
            message = this.message,
            type = type,
            timeStamp = this.timeStamp,
            sender = this.sender,
            senderId = this.senderId,
            receiver = this.receiver,
            receiverId = this.receiverId,
            status = this.status?.value,
            viewType = this.viewType?.value
    )

    return conversationFirebase
}

fun ConversationFirebase.toLocal(myId: String): Conversation {
    val convType = when (this.type?.id) {
        0 -> {
            Gson().fromJson(this.type?.data, ConversationType.Video::class.java)
        }
        1 -> {
            Gson().fromJson(this.type?.data, ConversationType.Image::class.java)
        }
        else -> {
            Gson().fromJson(this.type?.data, ConversationType.Message::class.java)

        }
    }
    val conversation = Conversation(
            id = this.id,
            message = this.message,
            type = convType,
            timeStamp = this.timeStamp,
            sender = this.sender,
            senderId = this.senderId,
            receiver = this.receiver,
            receiverId = this.receiverId,
            status = ConversationStatus.values()[this.status!!],
            viewType = if (myId == senderId) ConversationViewType.LEFT else ConversationViewType.RIGHT
    )

    return conversation
}

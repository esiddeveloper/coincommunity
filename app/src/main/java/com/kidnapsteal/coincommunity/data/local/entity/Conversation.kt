package com.kidnapsteal.coincommunity.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Conversation(
        @PrimaryKey
        var id: String = "",
        var message: String? = null,
        var type: ConversationType? = null,
        var timeStamp: Long = 0L,
        var sender: User? = null,
        var senderId: String? = null,
        var receiver: User? = null,
        var receiverId: String? = null,
        var status: ConversationStatus? = null,
        var viewType: ConversationViewType? = null
)
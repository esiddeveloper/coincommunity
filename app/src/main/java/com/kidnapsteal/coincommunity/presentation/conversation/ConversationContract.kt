package com.kidnapsteal.coincommunity.presentation.conversation

import com.kidnapsteal.coincommunity.BasePresenter
import com.kidnapsteal.coincommunity.BaseView
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationType

interface ConversationContract {

    interface Presenter : BasePresenter<View> {
        fun sendMessage(message: String, type: ConversationType)
        fun deleteMessage(messageId: String)
    }

    interface View : BaseView {
        fun renderConversation(conversations: List<Conversation>)
    }
}
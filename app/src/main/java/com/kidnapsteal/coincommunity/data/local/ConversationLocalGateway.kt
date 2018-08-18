package com.kidnapsteal.coincommunity.data.local

import com.kidnapsteal.coincommunity.data.local.dao.ConversationDao
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

interface ConversationLocalGateway {
    fun getConversations(userId: String): Flowable<List<Conversation>>
    fun sendConversation(conversation: Conversation): Completable
    fun insertConversations(conversations: List<Conversation>): Completable
    fun deleteConversation(conversationId: String): Completable
}

class ConversationLocalGatewayImpl @Inject constructor(private val dao: ConversationDao) : ConversationLocalGateway {
    override fun insertConversations(conversations: List<Conversation>): Completable {
        return Completable.fromCallable {
            dao.insertConversations(conversations)
        }
    }

    override fun getConversations(userId: String): Flowable<List<Conversation>> {
        return dao.getConversations(userId)
    }

    override fun sendConversation(conversation: Conversation): Completable {
        return Completable.fromCallable {
            dao.insertConversation(conversation)
        }
    }

    override fun deleteConversation(conversationId: String): Completable {
        return Completable.fromCallable {
            dao.deleteById(conversationId)
        }
    }

}
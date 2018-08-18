package com.kidnapsteal.coincommunity.data

import com.kidnapsteal.coincommunity.data.local.ConversationLocalGateway
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationType
import com.kidnapsteal.coincommunity.data.local.entity.User
import com.kidnapsteal.coincommunity.data.local.entity.generate
import com.kidnapsteal.coincommunity.data.remote.ConversationRemoteGateway
import com.kidnapsteal.coincommunity.util.Ln
import com.kidnapsteal.coincommunity.util.MockData.mockId
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface ConversationRepository {

    fun getConversations(userId: String): Flowable<List<Conversation>>
    fun sendConversation(userId: String, message: String, type: ConversationType): Completable
    fun deleteConversation(userId: String, id: String): Completable

}

class ConversationRepositoryImpl @Inject constructor(
        private val local: ConversationLocalGateway,
        private val remote: ConversationRemoteGateway
) : ConversationRepository {
    override fun getConversations(userId: String): Flowable<List<Conversation>> {
        return Flowable.combineLatest(getLocalConv(userId), getRemoteConv(userId), BiFunction { t1, t2 ->
            t1
        })
    }

    override fun sendConversation(userId: String, message: String, type: ConversationType): Completable {
        //TODO fix me later get users
        val sender = User(userId = mockId())
        val receiver = User(userId = userId)
        val conversation = Conversation().generate(sender, receiver, message, type)
        return local.sendConversation(conversation).andThen(remote.sendConversation(conversation))
    }

    override fun deleteConversation(userId: String, id: String): Completable {
        return remote.deleteConversation(userId, id).andThen(local.deleteConversation(id))
    }

    private fun getLocalConv(userId: String): Flowable<List<Conversation>> {
        return local.getConversations(userId).filter {
            it.isNotEmpty()
        }.doOnError {
            Ln.e("HERE is the error $it")
        }
    }

    private fun getRemoteConv(userId: String): Flowable<List<Conversation>> {
        return remote.getConversations(userId)
                .doOnNext {
                    local.insertConversations(it).subscribeOn(Schedulers.computation()).subscribe()
                }
    }

}
package com.kidnapsteal.coincommunity.domain.chat

import com.kidnapsteal.coincommunity.data.ConversationRepository
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import io.reactivex.Flowable
import javax.inject.Inject

interface GetConversationUseCase {
    fun execute(userId: String): Flowable<List<Conversation>>
}

class GetConversationUseCaseImpl @Inject constructor(
        private val repository: ConversationRepository) : GetConversationUseCase {

    override fun execute(userId: String): Flowable<List<Conversation>> {
        return repository.getConversations(userId)
    }
}
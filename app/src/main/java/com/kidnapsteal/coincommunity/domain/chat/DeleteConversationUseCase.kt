package com.kidnapsteal.coincommunity.domain.chat

import com.kidnapsteal.coincommunity.data.ConversationRepository
import io.reactivex.Completable
import javax.inject.Inject

interface DeleteConversationUseCase {
    fun execute(userId: String, conversationId: String): Completable
}

class DeleteConversationUseCaseImpl @Inject constructor(
        private val repository: ConversationRepository
) : DeleteConversationUseCase {
    override fun execute(userId: String, conversationId: String): Completable {
        return repository.deleteConversation(userId, conversationId)
    }

}
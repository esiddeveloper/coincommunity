package com.kidnapsteal.coincommunity.domain.chat

import com.kidnapsteal.coincommunity.data.ConversationRepository
import com.kidnapsteal.coincommunity.data.local.entity.ConversationType
import io.reactivex.Completable
import javax.inject.Inject

interface SendConversationUseCase {
    fun execute(userId: String, message: String, type: ConversationType): Completable
}

class SendConversationUseCaseImpl @Inject constructor(
        private val repository: ConversationRepository
) : SendConversationUseCase {

    override fun execute(userId: String, message: String, type: ConversationType): Completable {
        return repository.sendConversation(userId, message, type)
    }

}
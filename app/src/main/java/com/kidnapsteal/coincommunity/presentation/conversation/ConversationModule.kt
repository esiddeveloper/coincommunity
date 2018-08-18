package com.kidnapsteal.coincommunity.presentation.conversation

import com.kidnapsteal.coincommunity.domain.chat.DeleteConversationUseCase
import com.kidnapsteal.coincommunity.domain.chat.GetConversationUseCase
import com.kidnapsteal.coincommunity.domain.chat.SendConversationUseCase
import com.kidnapsteal.coincommunity.util.RxScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ConversationModule {

    companion object {
        const val NAMED_CONVERSATION_USER_ID = "Conversation.NAMED_USER_ID"
    }

    @Provides
    @Named(NAMED_CONVERSATION_USER_ID)
    fun provideUserId(activity: ConversationActivity): String {
        return activity.intent.getStringExtra(ConversationActivity.USER_ID)
    }

    @Provides
    fun providePresenter(@Named(NAMED_CONVERSATION_USER_ID) userId: String,
                         getConversationUseCase: GetConversationUseCase,
                         sendConversationUseCase: SendConversationUseCase,
                         deleteConversationUseCase: DeleteConversationUseCase,
                         rxScheduler: RxScheduler): ConversationContract.Presenter {
        return ConversationPresenter(
                userId,
                getConversationUseCase,
                sendConversationUseCase,
                deleteConversationUseCase,
                rxScheduler)
    }

}
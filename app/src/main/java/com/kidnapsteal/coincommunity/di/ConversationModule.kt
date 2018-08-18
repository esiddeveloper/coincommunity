package com.kidnapsteal.coincommunity.di

import com.kidnapsteal.coincommunity.data.ConversationRepository
import com.kidnapsteal.coincommunity.data.ConversationRepositoryImpl
import com.kidnapsteal.coincommunity.data.local.ConversationLocalGateway
import com.kidnapsteal.coincommunity.data.local.ConversationLocalGatewayImpl
import com.kidnapsteal.coincommunity.data.remote.ConversationRemoteGateway
import com.kidnapsteal.coincommunity.data.remote.ConversationRemoteGatewayImpl
import com.kidnapsteal.coincommunity.domain.chat.*
import dagger.Binds
import dagger.Module

@Module
abstract class ConversationModule {

    @Binds
    abstract fun bindGetConversation(getConversationUseCaseImpl: GetConversationUseCaseImpl): GetConversationUseCase

    @Binds
    abstract fun bindSendConversation(sendConversationUseCaseImpl: SendConversationUseCaseImpl): SendConversationUseCase

    @Binds
    abstract fun bindDeleteConversation(deleteConversationUseCaseImpl: DeleteConversationUseCaseImpl): DeleteConversationUseCase

    @Binds
    abstract fun bindConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

    @Binds
    abstract fun bindConversationRemoteGateway(conversationRemoteGateway: ConversationRemoteGatewayImpl): ConversationRemoteGateway

    @Binds
    abstract fun bindConversationLocalGateway(conversationLocalGatewayImpl: ConversationLocalGatewayImpl): ConversationLocalGateway


}
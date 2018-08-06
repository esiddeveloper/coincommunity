package com.kidnapsteal.coincommunity.di

import com.kidnapsteal.coincommunity.data.UserRepository
import com.kidnapsteal.coincommunity.data.UserRepositoryImpl
import com.kidnapsteal.coincommunity.data.local.UserLocalGateway
import com.kidnapsteal.coincommunity.data.local.UserLocalGatewayImpl
import com.kidnapsteal.coincommunity.data.remote.UserRemoteGateway
import com.kidnapsteal.coincommunity.data.remote.UserRemoteGatewayImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UserModule{

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindUserRemoteGateway(userRemoteGatewayImpl: UserRemoteGatewayImpl): UserRemoteGateway

    @Binds
    abstract fun bindUserLocalGateway(localGatewayImpl: UserLocalGatewayImpl): UserLocalGateway
}
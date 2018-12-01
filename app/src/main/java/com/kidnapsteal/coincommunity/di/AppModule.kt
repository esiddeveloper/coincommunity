package com.kidnapsteal.coincommunity.di

import android.app.Application
import android.content.Context
import com.kidnapsteal.coincommunity.data.UserRepository
import com.kidnapsteal.coincommunity.data.UserRepositoryImpl
import com.kidnapsteal.coincommunity.data.local.UserLocalGateway
import com.kidnapsteal.coincommunity.data.local.UserLocalGatewayImpl
import com.kidnapsteal.coincommunity.data.remote.UserRemoteGateway
import com.kidnapsteal.coincommunity.data.remote.UserRemoteGatewayImpl
import com.kidnapsteal.coincommunity.util.RxScheduler
import com.kidnapsteal.coincommunity.util.RxSchedulerImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(app: Application): Context

    @Binds
    abstract fun bindRxScheduler(rxSchedulerImpl: RxSchedulerImpl): RxScheduler

}
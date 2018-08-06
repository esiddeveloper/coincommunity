package com.kidnapsteal.coincommunity.di

import android.app.Application
import com.kidnapsteal.coincommunity.CoinCommunity
import com.kidnapsteal.coincommunity.presentation.friend.FriendModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DatabaseModule::class,
    FirebaseModule::class,
    AppModule::class,
    UserModule::class,
    FriendModule::class,
    ActivityBindingModule::class,
    AndroidSupportInjectionModule::class
])
interface AppComponent : AndroidInjector<CoinCommunity> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}
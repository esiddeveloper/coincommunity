package com.kidnapsteal.coincommunity.di

import com.kidnapsteal.coincommunity.MainActivity
import com.kidnapsteal.coincommunity.presentation.friend.FriendModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [FriendModule::class])
    abstract fun mainActivity(): MainActivity
}
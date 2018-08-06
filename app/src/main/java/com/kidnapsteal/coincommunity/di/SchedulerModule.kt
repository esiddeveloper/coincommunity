package com.kidnapsteal.coincommunity.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulerModule{

    companion object {
        const val NAME_UI_THREAD = "ui_scheduler"
        const val NAME_IO_THREAD = "io_scheduler"
        const val NAME_COMPUTE_THREAD = "compute_scheduler"
        const val NAME_NEW_THREAD = "new_thread_scheduler"
    }

    @Provides
    @Named(NAME_UI_THREAD)
    fun provideUIScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    @Named(NAME_IO_THREAD)
    fun provideIOScheduler(): Scheduler = Schedulers.io()

    @Singleton
    @Provides
    @Named(NAME_COMPUTE_THREAD)
    fun provideComputeScheduler(): Scheduler = Schedulers.computation()

    @Singleton
    @Provides
    @Named(NAME_NEW_THREAD)
    fun provideNewThreadScheduler(): Scheduler = Schedulers.newThread()

}
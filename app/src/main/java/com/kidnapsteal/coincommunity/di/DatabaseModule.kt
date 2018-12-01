package com.kidnapsteal.coincommunity.di

import android.app.Application
import androidx.room.Room
import com.kidnapsteal.coincommunity.data.local.dao.ConversationDao
import com.kidnapsteal.coincommunity.data.local.dao.UserDao
import com.kidnapsteal.coincommunity.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class DatabaseModule {

    companion object {
        const val NAME_DB = "db_name"
        const val DATABASE_NAME = "coin_community.db"
    }

    @Provides
    @Named(NAME_DB)
    fun provideDbName(): String = DATABASE_NAME

    @Singleton
    @Provides
    fun provideDatabase(context: Application, @Named(NAME_DB) dbName: String): AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, dbName)
                .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Singleton
    @Provides
    fun provideConversationDao(appDatabase: AppDatabase): ConversationDao = appDatabase.conversationDao()
}
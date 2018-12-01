package com.kidnapsteal.coincommunity.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kidnapsteal.coincommunity.data.local.dao.ConversationDao
import com.kidnapsteal.coincommunity.data.local.dao.UserDao
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationConverter
import com.kidnapsteal.coincommunity.data.local.entity.Converters
import com.kidnapsteal.coincommunity.data.local.entity.User

@Database(entities = [User::class, Conversation::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class, ConversationConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun conversationDao(): ConversationDao
}
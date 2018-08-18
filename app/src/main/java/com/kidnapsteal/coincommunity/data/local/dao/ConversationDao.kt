package com.kidnapsteal.coincommunity.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import io.reactivex.Flowable

@Dao
interface ConversationDao {

    @Query("SELECT * FROM Conversation WHERE senderId= :userId OR receiverId= :userId")
    fun getConversations(userId: String): Flowable<List<Conversation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversation(conversation: Conversation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversations(conversations: List<Conversation>)

    @Query("DELETE FROM Conversation WHERE id= :conversationId")
    fun deleteById(conversationId: String)

}
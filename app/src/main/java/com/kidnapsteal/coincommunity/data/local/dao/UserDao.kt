package com.kidnapsteal.coincommunity.data.local.dao

import androidx.room.*
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :userId")
    fun getUserById(userId: String): Single<User>

    @Query("SELECT * FROM User")
    fun getAllUser(): Maybe<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun deleteUser(user: User)
}
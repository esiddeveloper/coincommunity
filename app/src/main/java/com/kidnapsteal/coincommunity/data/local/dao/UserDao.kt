package com.kidnapsteal.coincommunity.data.local.dao

import androidx.room.*
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Single<User>

    @Query("SELECT * FROM users")
    fun getAllUser(): Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun deleteUser(user: User)
}
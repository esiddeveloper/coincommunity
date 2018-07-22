package com.kidnapsteal.coincommunity.data.local.dao

import androidx.room.*
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): Single<User>

    @Query("SELECT * FROM users")
    fun getAllUser(): Single<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User) : Completable

    @Delete
    fun deleteUser(user: User): Completable
}
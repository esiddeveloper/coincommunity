package com.kidnapsteal.coincommunity.data

import com.kidnapsteal.coincommunity.data.local.LocalGateway
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getUserById(id: String): Single<User>
    fun getAllUser(): Single<List<User>>
    fun deleteUser(user: User): Completable
    fun insertUsers(users: List<User>): Completable
    fun insertUser(user: User): Completable
}

class ChatRepositoryImpl(private val local: LocalGateway) : UserRepository {
    override fun getUserById(id: String): Single<User> = local.getUserById(id)
    override fun getAllUser(): Single<List<User>> = local.getAllUser()
    override fun deleteUser(user: User): Completable = local.deleteUser(user)
    override fun insertUsers(users: List<User>): Completable = local.insertUsers(users)
    override fun insertUser(user: User): Completable = local.insertUser(user)
}
package com.kidnapsteal.coincommunity.data.local

import com.kidnapsteal.coincommunity.data.local.dao.UserDao
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Completable
import io.reactivex.Single

interface LocalGateway {
    fun getUserById(id: String): Single<User>
    fun getAllUser(): Single<List<User>>
    fun deleteUser(user: User): Completable
    fun insertUsers(users: List<User>): Completable
    fun insertUser(user: User): Completable
}

class LocalGatewayImpl(private val userDao: UserDao) : LocalGateway {
    override fun getUserById(id: String): Single<User> = userDao.getUserById(id)
    override fun getAllUser(): Single<List<User>> = userDao.getAllUser()
    override fun deleteUser(user: User): Completable = userDao.deleteUser(user)
    override fun insertUsers(users: List<User>): Completable = userDao.insertAll(users)
    override fun insertUser(user: User): Completable = userDao.insert(user)

}
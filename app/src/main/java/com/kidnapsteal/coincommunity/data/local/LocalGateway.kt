package com.kidnapsteal.coincommunity.data.local

import com.kidnapsteal.coincommunity.data.local.dao.UserDao
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface LocalGateway {
    fun getUserById(id: String): Single<User>
    fun getAllUser(): Observable<List<User>>
    fun deleteUser(user: User): Completable
    fun insertUsers(users: List<User>): Completable
    fun insertUser(user: User): Completable
}

class LocalGatewayImpl(private val userDao: UserDao) : LocalGateway {
    override fun getUserById(id: String): Single<User> = userDao.getUserById(id)
    override fun getAllUser(): Observable<List<User>> = userDao.getAllUser().toObservable()
    override fun deleteUser(user: User): Completable = Completable.fromAction {
        userDao.deleteUser(user)
    }

    override fun insertUsers(users: List<User>): Completable = Completable.fromAction {
        userDao.insertAll(users)
    }

    override fun insertUser(user: User): Completable = Completable.fromAction {
        userDao.insert(user)
    }

}
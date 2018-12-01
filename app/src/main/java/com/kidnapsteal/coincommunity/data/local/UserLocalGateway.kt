package com.kidnapsteal.coincommunity.data.local

import com.kidnapsteal.coincommunity.data.local.dao.UserDao
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.*
import javax.inject.Inject
import javax.inject.Singleton

interface UserLocalGateway {
    fun getUserById(id: String): Single<User>
    fun getAllUser(): Observable<List<User>>
    fun deleteUser(user: User): Completable
    fun insertUsers(users: List<User>): Completable
    fun insertUser(user: User): Completable
}

@Singleton
class UserLocalGatewayImpl @Inject constructor(
        private val userDao: UserDao) : UserLocalGateway {
    override fun getUserById(id: String): Single<User> = userDao.getUserById(id)
    override fun getAllUser(): Observable<List<User>> = userDao.getAllUser().toObservable()
    override fun deleteUser(user: User): Completable = Completable.fromCallable {
        userDao.deleteUser(user)
    }

    override fun insertUsers(users: List<User>): Completable {
        return Completable.fromCallable { userDao.insertAll(users) }

    }

    override fun insertUser(user: User): Completable = Completable.fromCallable {
        userDao.insert(user)
    }

}
package com.kidnapsteal.coincommunity.data

import com.kidnapsteal.coincommunity.data.local.UserLocalGateway
import com.kidnapsteal.coincommunity.data.local.entity.User
import com.kidnapsteal.coincommunity.data.remote.UserRemoteGateway
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    fun getUserById(id: String): Single<User>
    fun getAllUser(): Observable<List<User>>
    fun deleteUser(friendUid: String): Completable
    fun addFriend(friendUid: String): Completable
}

@Singleton
class UserRepositoryImpl @Inject constructor(
        private val userLocal: UserLocalGateway,
        private val remote: UserRemoteGateway) : UserRepository {

    override fun getUserById(id: String): Single<User> {
        return userLocal.getUserById(id).onErrorResumeNext {
            remote.getUser(id)
        }
    }

    override fun getAllUser(): Observable<List<User>> {
        return Observable.concat(getFriendFromLocal(), getFriendFromRemote())
    }

    override fun deleteUser(friendUid: String): Completable {
        return getUserFromLocal(friendUid)
                .flatMapCompletable {
                    remote.removeFriend(friendUid)
                            .mergeWith(userLocal.deleteUser(it))
                }
    }

    override fun addFriend(friendUid: String): Completable {
        return remote.addFriend(friendUid)
        //todo add for local remove/checking
    }


    private fun getFriendFromRemote() =
            remote.getFriendList()
                    .doOnNext {
                        insertUsersToLocal(it).subscribeOn(Schedulers.computation()).subscribe()
                    }.toObservable()


    private fun getFriendFromLocal(): Observable<List<User>> {
        return userLocal.getAllUser().filter { it.isNotEmpty() }
    }

    private fun insertUsersToLocal(users: List<User>): Completable = userLocal.insertUsers(users)
    private fun getUserFromLocal(uid: String): Single<User> = userLocal.getUserById(uid)
}
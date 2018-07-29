package com.kidnapsteal.coincommunity.data

import com.kidnapsteal.coincommunity.data.local.LocalGateway
import com.kidnapsteal.coincommunity.data.local.entity.User
import com.kidnapsteal.coincommunity.data.remote.UserRemoteGateway
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun getUserById(id: String): Single<User>
    fun getAllUser(): Observable<List<User>>
    fun deleteUser(friendUid: String): Completable
    fun addFriend(friendUid: String): Completable
}

class UserRepositoryImpl(private val local: LocalGateway,
                         private val remote: UserRemoteGateway) : UserRepository {
    override fun getUserById(id: String): Single<User> {
        return local.getUserById(id).onErrorResumeNext {
            remote.getUser(id)
        }
    }

    override fun getAllUser(): Observable<List<User>> {
        return getFriendFromLocal().concatWith(getFriendFromRemote())
    }

    override fun deleteUser(friendUid: String): Completable {
        return getUserFromLocal(friendUid)
                .flatMapCompletable {
                    remote.removeFriend(friendUid)
                            .mergeWith(local.deleteUser(it))
                }
    }

    override fun addFriend(friendUid: String): Completable {
        return getUserFromLocal(friendUid)
                .flatMapCompletable {
                    remote.addFriend(friendUid)
                            .mergeWith(local.insertUser(it))
                }
    }


    private fun getFriendFromRemote() =
            remote.getFriendList()
                    .doOnNext {
                        insertUsersToLocal(it)
                    }


    private fun getFriendFromLocal(): Observable<List<User>> {
        return local.getAllUser().filter {
            it.isNotEmpty()
        }
    }

    private fun insertUsersToLocal(users: List<User>): Completable = local.insertUsers(users)
    private fun insertUserToLocal(user: User): Completable = local.insertUser(user)
    private fun getUserFromLocal(uid: String): Single<User> = local.getUserById(uid)
}
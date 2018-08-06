package com.kidnapsteal.coincommunity.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kidnapsteal.coincommunity.data.local.entity.User
import com.kidnapsteal.coincommunity.data.remote.firebase.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

interface UserRemoteGateway {
    fun getUser(uid: String): Single<User>
    fun getFriendList(): Flowable<List<User>>
    fun putUser(user: User): Completable
    fun addFriend(friendUid: String): Completable
    fun removeFriend(friendUid: String): Completable
}

@Singleton
class UserRemoteGatewayImpl @Inject constructor(
        private val auth: FirebaseAuth,
        private val firestore: FirebaseFirestore) : UserRemoteGateway {

    //todo remove hardcoded ID == anjonjeng
    override fun removeFriend(friendUid: String): Completable {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = /*currentUser.uid*/ "anjonjeng"
            val db = firestore.collection("user").document(uid).collection("friends")
                    .document(friendUid)
            return removeFriendTask(db.delete())
        } else {
            return Completable.error(Exception("Need To Login"))
        }
    }

    override fun addFriend(friendUid: String): Completable {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = /*currentUser.uid*/ "anjonjeng"
            val db = firestore.collection("user")
            val self = db.document(uid).collection("friends").document(friendUid)
            val friend = db.document(friendUid).collection("friends").document(uid)

            val completableSelf = getUser(uid).flatMapCompletable {
                addFriendTask(friend.set(it))
            }

            val completableFriend = getUser(friendUid).flatMapCompletable {
                addFriendTask(self.set(it))
            }

            return completableSelf.mergeWith(completableFriend)
        } else {
            return Completable.error(Exception("Need To Login"))
        }

    }

    override fun getFriendList(): Flowable<List<User>> {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = /*currentUser.uid*/ "anjonjeng"
            val db = firestore.collection("user").document(uid)
                    .collection("friends")
            return getFlowableRealTimeTask(db).map {
                if (it.isNotEmpty()) {
                    it.map {
                        it.toObject(User::class.java)
                    }
                } else {
                    listOf()
                }
            }
        } else {
            return Flowable.error(Exception("Need To Login"))
        }
    }

    override fun putUser(user: User): Completable {
        val db = firestore.collection("user").document(user.userId)
                .collection("profile").document("details")
        return updateProfile(db.set(user, SetOptions.merge()))
    }

    override fun getUser(uid: String): Single<User> {
        val db = firestore.collection("user").document(uid)
                .collection("profile").document("details")

        return getUserDetail(db.get()).map {
            if (it.exists()) {
                it.toObject(User::class.java)!!
            } else {
                User()
            }
        }
    }

}
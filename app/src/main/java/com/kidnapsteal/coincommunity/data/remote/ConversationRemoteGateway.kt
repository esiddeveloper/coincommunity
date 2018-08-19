package com.kidnapsteal.coincommunity.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationFirebase
import com.kidnapsteal.coincommunity.data.local.entity.toFirebase
import com.kidnapsteal.coincommunity.data.local.entity.toLocal
import com.kidnapsteal.coincommunity.data.remote.firebase.addFriendTask
import com.kidnapsteal.coincommunity.data.remote.firebase.deleteTask
import com.kidnapsteal.coincommunity.data.remote.firebase.getFlowableRealTimeTask
import com.kidnapsteal.coincommunity.util.Ln
import com.kidnapsteal.coincommunity.util.MockData.mockId
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

interface ConversationRemoteGateway {
    fun getConversations(userId: String): Flowable<List<Conversation>>
    fun sendConversation(conversation: Conversation): Completable
    fun deleteConversation(userId: String, conversationId: String): Completable
}

class ConversationRemoteGatewayImpl @Inject constructor(
        private val auth: FirebaseAuth,
        private val firestore: FirebaseFirestore) : ConversationRemoteGateway {

    override fun getConversations(userId: String): Flowable<List<Conversation>> {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            val uid = /*currentUser.uid*/ mockId()

            val db = firestore.collection("user").document(uid).collection("chats")
                    .document(userId).collection("message")
            getFlowableRealTimeTask(db).map {
                if (it.isNotEmpty()) {
                    cleanConversations(userId)
                    it.map { snapshot ->
                        snapshot.toObject(ConversationFirebase::class.java).toLocal(uid)
                    }

                } else {
                    listOf()
                }
            }
        } else {
            Flowable.error(Exception("Need To Login"))
        }
    }

    override fun sendConversation(conversation: Conversation): Completable {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            val uid = /*currentUser.uid*/ mockId()

            val receiverId = conversation.receiver?.userId!!

            val db = firestore.collection("user").document(receiverId).collection("chats")
                    .document(uid).collection("message").document(conversation.id)
            addFriendTask(db.set(conversation.toFirebase()))
        } else {
            Completable.error(Exception("Need To Login"))
        }
    }

    override fun deleteConversation(userId: String, conversationId: String): Completable {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            val uid = /*currentUser.uid*/ mockId()
            val db = firestore.collection("user").document(userId).collection("chats")
                    .document(uid).collection("message").document(conversationId)
            deleteTask(db.delete())
        } else {
            Completable.error(Exception("Need To Login"))
        }
    }

    //delete all conversations on firebase as user already read it so keep storage low as possible
    private fun cleanConversations(userId: String) {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            val uid = /*currentUser.uid*/ mockId()
            val db = firestore.collection("user").document(uid).collection("chats")
                    .document(userId).collection("message")

            val data = db.get().addOnSuccessListener { querySnapshot ->
                querySnapshot.map { rawItem ->
                    rawItem.toObject(ConversationFirebase::class.java)
                }.forEach { conversation ->
                    db.document(conversation.id).delete()
                }
            }.addOnFailureListener {
                Ln.e("ConversationRemoteGateway", "cleanConversation --- ${it.message}")
            }
        } else {
            Ln.e("ConversationRemoteGateway", "cleanConversation --- failed to authenticate")

        }
    }

}
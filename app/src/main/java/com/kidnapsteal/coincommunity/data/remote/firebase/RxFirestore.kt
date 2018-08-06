package com.kidnapsteal.coincommunity.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import io.reactivex.*

fun updateProfile(task: Task<Void>): Completable {
    return Completable.create {
        CompletableFirestore.assignOnTask(it, task)
    }
}

fun addFriendTask(task: Task<Void>): Completable {
    return Completable.create {
        CompletableFirestore.assignOnTask(it, task)
    }
}

fun removeFriendTask(task: Task<Void>): Completable {
    return Completable.create {
        CompletableFirestore.assignOnTask(it, task)
    }
}

fun getUserDetail(task: Task<DocumentSnapshot>): Single<DocumentSnapshot> {
    return Single.create {
        SingleFirestore.assignOnTask(it, task)
    }
}

fun <T> getObservableTask(task: Task<T>): Observable<T> {
    return Observable.create {
        ObservableFirestore.assingOnTask(it, task)
    }
}

fun <T> getFlowableTask(task: Task<T>): Flowable<T> {
    return Flowable.create({
        FlowableFirestore.assingOnTask(it, task)
    }, BackpressureStrategy.DROP)
}

fun getFlowableRealTimeTask(task: CollectionReference): Flowable<List<QueryDocumentSnapshot>> {
    return Flowable.create({
        task.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                it.onError(firebaseFirestoreException)
                it.onComplete()
            }

            if (querySnapshot?.size()!! > 0) {
                it.onNext(querySnapshot.map { it })
            }

        }
    }, BackpressureStrategy.DROP)
}
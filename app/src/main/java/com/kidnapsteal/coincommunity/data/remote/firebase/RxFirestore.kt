package com.kidnapsteal.coincommunity.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

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
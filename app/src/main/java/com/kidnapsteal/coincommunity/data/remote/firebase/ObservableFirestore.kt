package com.kidnapsteal.coincommunity.data.remote.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.reactivex.ObservableEmitter
import java.lang.Exception

class ObservableFirestore<T>(private val emitter: ObservableEmitter<T>) : OnSuccessListener<T>, OnFailureListener, OnCompleteListener<T> {

    override fun onSuccess(p0: T) {
        emitter.onNext(p0)
    }

    override fun onFailure(p0: Exception) {
        emitter.onError(p0)
    }

    override fun onComplete(p0: Task<T>) {
        if (p0.isComplete) {
            emitter.onComplete()
        }
    }

    companion object {
        fun <T> assingOnTask(emitter: ObservableEmitter<T>, task: Task<T>) {
            val handler = ObservableFirestore(emitter)
            task.addOnSuccessListener(handler)
            task.addOnFailureListener(handler)
            task.addOnCompleteListener(handler)
        }

    }

}
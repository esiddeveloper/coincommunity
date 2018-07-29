package com.kidnapsteal.coincommunity.data.remote.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.reactivex.CompletableEmitter
import java.lang.Exception


class CompletableFirestore<T>(private val emitter: CompletableEmitter) :
        OnSuccessListener<T>,
        OnFailureListener,
        OnCompleteListener<T> {
    override fun onSuccess(p0: T) {
        emitter.onComplete()
    }

    override fun onComplete(p0: Task<T>) {
        emitter.onComplete()
    }

    override fun onFailure(p0: Exception) {
        emitter.onError(p0)
    }

    companion object {

        fun <T> assignOnTask(emitter: CompletableEmitter, task: Task<T>) {
            val handler = CompletableFirestore<T>(emitter)
            task.addOnSuccessListener(handler)
            task.addOnFailureListener(handler)
            task.addOnCompleteListener(handler)
        }
    }
}
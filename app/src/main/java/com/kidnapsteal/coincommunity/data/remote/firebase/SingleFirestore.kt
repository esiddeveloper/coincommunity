package com.kidnapsteal.coincommunity.data.remote.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.reactivex.SingleEmitter
import java.lang.Exception


class SingleFirestore<T>(private val emitter: SingleEmitter<in T>) :
        OnSuccessListener<T>,
        OnFailureListener,
        OnCompleteListener<T> {

    override fun onSuccess(p0: T) {
        emitter.onSuccess(p0)
    }

    override fun onFailure(p0: Exception) {
        emitter.onError(p0)
    }

    override fun onComplete(p0: Task<T>) {
        //
    }

    companion object {

        fun <T> assignOnTask(emitter: SingleEmitter<in T>, task: Task<T>) {
            val handler = SingleFirestore(emitter)
            task.addOnSuccessListener(handler)
            task.addOnFailureListener(handler)
            task.addOnCompleteListener(handler)
        }
    }
}
package com.kidnapsteal.coincommunity.util

import android.util.Log
import com.kidnapsteal.coincommunity.BuildConfig

object Ln {

    enum class DebugType {
        DEBUG,
        ERROR,
        VERBOSE,
        INFO
    }

    fun d(throwable: Throwable) {
        log(DebugType.DEBUG, "Error", throwable.message!!)
    }

    fun d(title: String, message: String) {
        log(DebugType.DEBUG, title, message)
    }

    fun d(message: String) {
        log(DebugType.DEBUG, "", message)
    }

    fun e(throwable: Throwable) {
        log(DebugType.ERROR, "Error", throwable.message!!)
    }

    fun e(title: String, message: String) {
        log(DebugType.ERROR, title, message)
    }

    fun e(message: String) {
        log(DebugType.ERROR, "", message)
    }

    fun i(throwable: Throwable) {
        log(DebugType.INFO, "Error", throwable.message!!)
    }

    fun i(title: String, message: String) {
        log(DebugType.INFO, title, message)
    }

    fun i(message: String) {
        log(DebugType.INFO, "", message)
    }

    fun v(throwable: Throwable) {
        log(DebugType.VERBOSE, "Error", throwable.message!!)
    }

    fun v(title: String, message: String) {
        log(DebugType.VERBOSE, title, message)
    }

    fun v(message: String) {
        log(DebugType.VERBOSE, "", message)
    }

    private fun log(debugType: DebugType, title: String, message: String) {

        if (BuildConfig.DEBUG) {
            when (debugType) {
                DebugType.DEBUG -> Log.d(TAG, "$title \n $message")
                DebugType.ERROR -> Log.e(TAG, "$title \n $message")
                DebugType.VERBOSE -> Log.v(TAG, "$title \n $message")
                DebugType.INFO -> Log.i(TAG, "$title \n $message")
            }
        }
    }

    const val TAG = "kp-e-l"

}
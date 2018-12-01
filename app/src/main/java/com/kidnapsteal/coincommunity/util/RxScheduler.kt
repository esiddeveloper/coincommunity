package com.kidnapsteal.coincommunity.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

interface RxScheduler {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun compute(): Scheduler
    fun newThread(): Scheduler
}

@Singleton
class RxSchedulerImpl @Inject constructor() : RxScheduler {

    private val uiScheduler = AndroidSchedulers.mainThread()
    private val ioScheduler = Schedulers.io()
    private val computeScheduler = Schedulers.computation()
    private val newScheduler = Schedulers.newThread()

    override fun io(): Scheduler = ioScheduler

    override fun ui(): Scheduler = uiScheduler

    override fun compute(): Scheduler = computeScheduler

    override fun newThread(): Scheduler = newScheduler

}
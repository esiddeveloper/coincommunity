package com.kidnapsteal.coincommunity

interface BasePresenter<T> {

    fun attachView(view: T)

    fun detachView()
}
package com.kidnapsteal.coincommunity.presentation.friend

import android.util.Log
import com.kidnapsteal.coincommunity.domain.user.AddFriendUseCase
import com.kidnapsteal.coincommunity.domain.user.DeleteFriendUseCase
import com.kidnapsteal.coincommunity.domain.user.GetFriendUseCase
import com.kidnapsteal.coincommunity.util.Ln
import com.kidnapsteal.coincommunity.util.RxScheduler
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FriendPresenter @Inject constructor(
        private val getFriendUseCase: GetFriendUseCase,
        private val deleteFriendUseCase: DeleteFriendUseCase,
        private val addFriendUseCase: AddFriendUseCase,
        private val rxScheduler: RxScheduler
) : FriendContract.Presenter {

    private lateinit var disposeOnDestroy: CompositeDisposable
    private lateinit var view: FriendContract.View

    //todo remove later only for testing
    private var counter = 500


    override fun openChatScreen(friendId: String) {
    }

    override fun attachView(view: FriendContract.View) {
        this.view = view
        disposeOnDestroy = CompositeDisposable()
        getFriendList()
    }

    override fun detachView() {
        disposeOnDestroy.dispose()
    }

    private fun getFriendList() {
        getFriendUseCase.execute().composeView()
                .subscribe({
                    view.renderFriendList(it)
                    view.showProgress(false) //since it's observable/flowable cannot get the complete state
                }, {
                    Ln.e(it)
                })
                .let { disposeOnDestroy.add(it) }

    }

    override fun addFriend(friendId: String) {
        counter++
        val mockId = "kidnapsteal$counter"
        addFriendUseCase.execute(mockId).composeView()
                .subscribe({
                    Log.d("kp-e-l", "Add Friend Successfully")
                }, {
                    Ln.e(it)
                }).let {
                    disposeOnDestroy.add(it)
                }
    }

    override fun deleteFriend(friendId: String) {
        deleteFriendUseCase.execute(friendId).composeView()
                .subscribe({
                    Log.d("kp-e-l", "Delete Friend Successfully")
                }, {
                    Ln.e(it)
                }).let {
                    disposeOnDestroy.add(it)
                }
    }

    private fun Completable.composeView(): Completable {
        return this.subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
    }

    private fun <T> Observable<T>.composeView(): Observable<T> {
        return this.subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doOnSubscribe { view.showProgress(true) }
                .doOnTerminate { view.showProgress(false) }
    }

}
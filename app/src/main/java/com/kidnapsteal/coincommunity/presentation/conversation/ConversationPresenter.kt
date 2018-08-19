package com.kidnapsteal.coincommunity.presentation.conversation

import com.kidnapsteal.coincommunity.data.local.entity.ConversationType
import com.kidnapsteal.coincommunity.domain.chat.DeleteConversationUseCase
import com.kidnapsteal.coincommunity.domain.chat.GetConversationUseCase
import com.kidnapsteal.coincommunity.domain.chat.SendConversationUseCase
import com.kidnapsteal.coincommunity.util.Ln
import com.kidnapsteal.coincommunity.util.RxScheduler
import io.reactivex.disposables.CompositeDisposable

class ConversationPresenter(
        private val userId: String,
        private val getConversationUseCase: GetConversationUseCase,
        private val sendConversationUseCase: SendConversationUseCase,
        private val deleteConversationUseCase: DeleteConversationUseCase,
        private val rxScheduler: RxScheduler) : ConversationContract.Presenter {

    private lateinit var view: ConversationContract.View
    private val disposeOnDestroy = CompositeDisposable()

    override fun attachView(view: ConversationContract.View) {
        this.view = view
        loadConversations()
    }

    override fun detachView() {
        disposeOnDestroy.clear()
    }

    override fun sendMessage(message: String, type: ConversationType) {
        sendConversationUseCase.execute(userId, message, type)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .subscribe({
                    Ln.d("sendMessage --- successful")
                }, {
                    view.showError(it.message ?: "Unknown Error")
                })
                .let { disposeOnDestroy.add(it) }
    }

    override fun deleteMessage(messageId: String) {
        deleteConversationUseCase.execute(userId, messageId)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .subscribe({
                    Ln.d("deleteMessage --- successful")
                }, {
                    view.showError(it.message ?: "Unknown Error")
                })
                .let { disposeOnDestroy.add(it) }
    }

    private fun loadConversations() {
        getConversationUseCase.execute(userId)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .subscribe({
                    view.renderConversation(it.sortedBy { it.timeStamp })
                }, {
                    view.showError(it.message ?: "Unknown Error")
                })
                .let { disposeOnDestroy.add(it) }
    }
}
package com.kidnapsteal.coincommunity.presentation.conversation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidnapsteal.coincommunity.R
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationType
import com.kidnapsteal.coincommunity.util.Ln
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_conversation.*
import javax.inject.Inject

class ConversationActivity : DaggerAppCompatActivity(), ConversationContract.View {

    companion object {
        const val USER_ID = "Conversation.USER.ID"
        const val TAG = "ConversationActivity"
    }

    @Inject
    lateinit var presenter: ConversationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversation)

        initRecycler()

        actionSend.setOnClickListener {
            presenter.sendMessage(editMessage.text.toString(), ConversationType.Message(editMessage.text.toString()))
            editMessage.setText("")

        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun renderConversation(conversations: List<Conversation>) {
        (recyclerView.adapter as ConversationAdapter).updateData(conversations)
    }

    override fun showError(message: String) {
        Ln.e(TAG, "showError --- $message")
    }

    override fun showProgress(show: Boolean) {
        Ln.d(TAG, "showProgress --- $show")
    }

    private fun initRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        recyclerView.adapter = ConversationAdapter(mutableListOf()) { presenter.deleteMessage(it) }
    }
}

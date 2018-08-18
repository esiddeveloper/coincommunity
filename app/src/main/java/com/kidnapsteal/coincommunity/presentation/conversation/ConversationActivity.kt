package com.kidnapsteal.coincommunity.presentation.conversation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kidnapsteal.coincommunity.R
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationType
import com.kidnapsteal.coincommunity.data.local.entity.ConversationViewType
import com.kidnapsteal.coincommunity.util.DateUtil
import com.kidnapsteal.coincommunity.util.Ln
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_conversation.*
import javax.inject.Inject

class ConversationActivity : DaggerAppCompatActivity(), ConversationContract.View {
//
//    var counter = 0
//    val conv = (1..40).map {
//        counter++
//        Conversation(
//                id = "$it",
//                message = randomString(9),
//                timeStamp = System.currentTimeMillis() + (60000 * it),
//                viewType = if (it % 3 == 0) ConversationViewType.LEFT else ConversationViewType.RIGHT
//        )
//    }.toMutableList()

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
        recyclerView.smoothScrollToPosition(conversations.size - 1)
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

        recyclerView.adapter = ConversationAdapter(mutableListOf())
    }
}


class ConversationAdapter(private var data: MutableList<Conversation>) : RecyclerView.Adapter<ConversationViewHolder>() {

    fun updateData(newData: List<Conversation>) {
        Log.d("List nih", "lama : ${data.size}")
        Log.d("List nih", "Baru : ${newData.size}")
        val diffResult = DiffUtil.calculateDiff(ConversationDiffUtilCallback(data, newData))
        this.data.clear()
        this.data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val layoutRes = when (viewType) {
            ConversationViewType.LEFT.value -> R.layout.layout_conversation_left
            ConversationViewType.RIGHT.value -> R.layout.layout_conversation_right
            else -> -1
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)

        return ConversationViewHolder(view)
    }

    override fun getItemCount(): Int = data.size


    override fun getItemViewType(position: Int): Int {
        val viewType = data[position].viewType
        return when (viewType) {
            ConversationViewType.LEFT -> viewType.value
            ConversationViewType.RIGHT -> viewType.value
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindConversation(data[position])
    }
}

class ConversationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val avatar = view.findViewById<ImageView>(R.id.imageViewAvatar)
    private val name = view.findViewById<TextView>(R.id.textViewName)
    private val message = view.findViewById<TextView>(R.id.textViewMessage)
    private val time = view.findViewById<TextView>(R.id.textViewTimeStamp)

    fun bindConversation(conversation: Conversation) {

        message.text = conversation.message ?: ""
        time.text = DateUtil.getShortTime(conversation.timeStamp)
    }
}
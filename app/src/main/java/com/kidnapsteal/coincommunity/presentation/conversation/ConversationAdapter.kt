package com.kidnapsteal.coincommunity.presentation.conversation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.kidnapsteal.coincommunity.R
import com.kidnapsteal.coincommunity.data.local.entity.Conversation
import com.kidnapsteal.coincommunity.data.local.entity.ConversationViewType
import com.kidnapsteal.coincommunity.util.DateUtil

class ConversationAdapter(private var data: MutableList<Conversation>, private val action: (String) -> Unit) : RecyclerView.Adapter<ConversationViewHolder>() {

    fun updateData(newData: List<Conversation>) {
        val diffResult = DiffUtil.calculateDiff(ConversationDiffUtilCallback(data, newData), false)
        this.data.clear()
        this.data.addAll(newData)
        diffResult.dispatchUpdatesTo(DiffUtilDispatchAdapter(this))
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
        holder.itemView.setOnLongClickListener {
            showBottomSheetDialog(holder.itemView.context, position)
            true
        }
    }

    private fun showBottomSheetDialog(context: Context, position: Int) {
        val dialog = ConversationBottomSheet(context)
        dialog.show()
        dialog.actionClick = View.OnClickListener {
            when (it.id) {
                R.id.actionCancel -> {

                }
                R.id.actionDelete -> {
                    action(data[position].id)
                }
            }
            dialog.dismiss()
        }
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

class DiffUtilDispatchAdapter(private val adapter: RecyclerView.Adapter<ConversationViewHolder>) : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyDataSetChanged()
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position, count, payload)
    }

}
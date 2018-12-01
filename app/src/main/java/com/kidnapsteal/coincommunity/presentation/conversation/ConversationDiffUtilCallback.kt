package com.kidnapsteal.coincommunity.presentation.conversation

import androidx.recyclerview.widget.DiffUtil
import com.kidnapsteal.coincommunity.data.local.entity.Conversation

class ConversationDiffUtilCallback(
        private val oldList: List<Conversation>,
        private val newList: List<Conversation>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
package com.kidnapsteal.coincommunity

import androidx.recyclerview.widget.DiffUtil
import com.kidnapsteal.coincommunity.data.local.entity.User

class UserDiffUtilCallback(
        private val oldList: List<User>,
        private val newList: List<User>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].userId == newList[newItemPosition].userId
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
}
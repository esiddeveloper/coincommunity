package com.kidnapsteal.coincommunity.presentation.friend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kidnapsteal.coincommunity.UserDiffUtilCallback
import com.kidnapsteal.coincommunity.R
import com.kidnapsteal.coincommunity.data.local.entity.User

class FriendAdapter(
        private val clickListener: (String) -> Unit,
        private val longClickListener: (String) -> Unit) : RecyclerView.Adapter<FriendViewHolder>() {

    var data: List<User> = mutableListOf()
        set(value) {
            val diffResult = DiffUtil.calculateDiff(UserDiffUtilCallback(field, value))
            diffResult.dispatchUpdatesTo(this)
            field = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_friend_item, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bindView(data[position], clickListener, longClickListener)
    }
}

class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
    private val textViewPhoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)

    fun bindView(user: User, clickListener: (String) -> Unit, longClickListener: (String) -> Unit) {
        textViewName.text = user.firstName
        textViewPhoneNumber.text = user.phoneNumber

        itemView.setOnClickListener {
            clickListener(user.userId)
        }

        itemView.setOnLongClickListener {
            longClickListener(user.userId)
            return@setOnLongClickListener true
        }
    }
}
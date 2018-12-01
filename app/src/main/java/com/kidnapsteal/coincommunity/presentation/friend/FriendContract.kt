package com.kidnapsteal.coincommunity.presentation.friend

import com.kidnapsteal.coincommunity.BasePresenter
import com.kidnapsteal.coincommunity.BaseView
import com.kidnapsteal.coincommunity.data.local.entity.User

interface FriendContract{

    interface View : BaseView {
        fun renderFriendList(friends: List<User>)
    }

    interface Presenter: BasePresenter<View>{
        fun deleteFriend(friendId: String)
        fun openChatScreen(friendId: String)
        fun addFriend(friendId: String)
    }
}
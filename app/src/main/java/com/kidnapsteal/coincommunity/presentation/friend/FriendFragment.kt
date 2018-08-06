package com.kidnapsteal.coincommunity.presentation.friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kidnapsteal.coincommunity.R
import com.kidnapsteal.coincommunity.data.local.entity.User
import com.kidnapsteal.coincommunity.util.Ln
import com.kidnapsteal.coincommunity.util.show
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_friend.*
import javax.inject.Inject

class FriendFragment @Inject constructor() : DaggerFragment(), FriendContract.View {

    @Inject
    lateinit var presenter: FriendContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = FriendAdapter({ addFriend(it) }, { deleteFriend(it) })
    }

    private fun addFriend(friendId: String) {
        presenter.addFriend(friendId)
    }

    private fun deleteFriend(friendId: String) {
        presenter.deleteFriend(friendId)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun renderFriendList(friends: List<User>) {
        recyclerView.adapter.let {
            (it as FriendAdapter).data = friends
        }
    }

    override fun showError(message: String) {
        Ln.e(message)
    }

    override fun showProgress(show: Boolean) {
        progressBar.show(show)
    }
}
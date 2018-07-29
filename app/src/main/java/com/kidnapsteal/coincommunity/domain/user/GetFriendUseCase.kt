package com.kidnapsteal.coincommunity.domain.user

import com.kidnapsteal.coincommunity.data.UserRepository
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Observable

interface GetFriendUseCase {
    fun execute(): Observable<List<User>>
}

class GetFriendUseCaseImpl(private val repo: UserRepository) : GetFriendUseCase {
    override fun execute(): Observable<List<User>> = repo.getAllUser()
}
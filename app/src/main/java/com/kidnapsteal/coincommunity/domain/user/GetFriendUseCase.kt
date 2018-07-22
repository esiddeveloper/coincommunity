package com.kidnapsteal.coincommunity.domain.user

import com.kidnapsteal.coincommunity.data.UserRepository
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Single

interface GetFriendUseCase {
    fun execute(): Single<List<User>>
}

class GetFriendUseCaseImpl(private val repo: UserRepository) : GetFriendUseCase {
    override fun execute(): Single<List<User>> = repo.getAllUser()
}
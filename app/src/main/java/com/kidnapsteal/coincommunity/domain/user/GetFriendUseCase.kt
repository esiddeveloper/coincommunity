package com.kidnapsteal.coincommunity.domain.user

import com.kidnapsteal.coincommunity.data.UserRepository
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

interface GetFriendUseCase {
    fun execute(): Observable<List<User>>
}

@Singleton
class GetFriendUseCaseImpl @Inject constructor(
        private val repo: UserRepository) : GetFriendUseCase {

    override fun execute(): Observable<List<User>> = repo.getAllUser()
}
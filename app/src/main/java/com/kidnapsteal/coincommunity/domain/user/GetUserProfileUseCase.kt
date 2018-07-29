package com.kidnapsteal.coincommunity.domain.user

import com.kidnapsteal.coincommunity.data.UserRepository
import com.kidnapsteal.coincommunity.data.local.entity.User
import io.reactivex.Single

interface GetUserProfileUseCase {
    fun execute(uid: String): Single<User>
}

class GetUserProfileUseCaseImpl(private val userRepository: UserRepository) : GetUserProfileUseCase {
    override fun execute(uid: String): Single<User> {
        return userRepository.getUserById(uid)
    }

}
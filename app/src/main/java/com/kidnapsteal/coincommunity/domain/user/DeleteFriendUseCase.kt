package com.kidnapsteal.coincommunity.domain.user

import com.kidnapsteal.coincommunity.data.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

interface DeleteFriendUseCase {
    fun execute(uid: String): Completable
}

class DeleteFriendUseCaseImpl
@Inject constructor(private val userRepository: UserRepository) : DeleteFriendUseCase {
    override fun execute(uid: String): Completable {
        return userRepository.deleteUser(uid)
    }

}
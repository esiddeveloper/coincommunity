package com.kidnapsteal.coincommunity.domain.user

import com.kidnapsteal.coincommunity.data.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

interface AddFriendUseCase {
    fun execute(friendUid: String): Completable
}

class AddFriendUseCaseImpl
@Inject constructor(private val userRepository: UserRepository) : AddFriendUseCase {
    override fun execute(friendUid: String): Completable {
        return userRepository.addFriend(friendUid)
    }

}
package com.kidnapsteal.coincommunity.presentation.friend

import com.kidnapsteal.coincommunity.domain.user.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FriendModule {

    @ContributesAndroidInjector
    internal abstract fun friendFragment(): FriendFragment

    @Binds
    internal abstract fun friendPresenter(presenter: FriendPresenter): FriendContract.Presenter

    @Binds
    internal abstract fun getFriendUseCase(getFriendUseCaseImpl: GetFriendUseCaseImpl): GetFriendUseCase

    @Binds
    internal abstract fun addFriendUseCase(addFriendUseCaseImpl: AddFriendUseCaseImpl): AddFriendUseCase

    @Binds
    internal abstract fun deleteFriendUseCase(deleteFriendUseCaseImpl: DeleteFriendUseCaseImpl): DeleteFriendUseCase
}
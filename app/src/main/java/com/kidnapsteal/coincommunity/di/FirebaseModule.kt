package com.kidnapsteal.coincommunity.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provudeFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}
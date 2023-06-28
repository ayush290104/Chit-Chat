package com.example.chit_chat.injection

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class module {
    @Singleton
    @Provides
fun providesFirebaseAuth():FirebaseAuth{
    return FirebaseAuth.getInstance()
}


}
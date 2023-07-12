package com.example.chit_chat.usecase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.chit_chat.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class loginsignup @Inject constructor(private val auth: FirebaseAuth,private val context: Activity){



   suspend fun signupwithemail(email:String,password:String) {
        var s = ""
       try {
           auth.createUserWithEmailAndPassword(email, password).await()
           auth.currentUser?.sendEmailVerification()
           Toast.makeText(context,"Please verify your email",Toast.LENGTH_SHORT).show()
       }catch (e:Exception){
           s= e.toString()
           Toast.makeText(context,e.toString().subSequence(59,s.length),Toast.LENGTH_SHORT).show()
       }
            }

suspend fun updateuser(string: String,uri:Uri?){
    val profileUpdates:UserProfileChangeRequest
    if (uri!=null){
         profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(string).setPhotoUri(uri)
            .build()
    }
    else{
         profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(string)
            .build()
    }


    try {
        auth.currentUser?.updateProfile(profileUpdates)?.await()
    }catch (e:java.lang.Exception){
        Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show()

    }

}

   suspend fun signinwithemail(email:String,password:String){
       var s = ""
       try {
           auth.signInWithEmailAndPassword(email, password).await()
       }catch (e:java.lang.Exception){
           s= e.toString()
           Toast.makeText(context,e.toString().subSequence(59,s.length),Toast.LENGTH_SHORT).show()
       }


    }
    fun checkuser():FirebaseUser? {
        return auth.currentUser
    }
    fun signout(){
        Firebase.auth.signOut()
    }

}

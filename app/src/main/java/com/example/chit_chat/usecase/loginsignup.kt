package com.example.chit_chat.usecase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.chit_chat.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
//        auth.createUserWithEmailAndPassword(email, password).await()
//            .addOnCompleteListener{ task ->
//                if (task.isSuccessful) {
//                       auth.currentUser?.sendEmailVerification()
//
//                } else {
//                    s = task.exception.toString()
//                    // If sign in fails, display a message to the user.
//
//                    Toast.makeText(context,task.exception.toString().subSequence(59,s.length),Toast.LENGTH_SHORT).show()
//                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
//
//
//                }
            }



    fun signinwithemail(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)

                }
            }
    }
    fun checkuser():FirebaseUser? {
        return auth.currentUser
    }
    fun signout(){
        Firebase.auth.signOut()
    }

}

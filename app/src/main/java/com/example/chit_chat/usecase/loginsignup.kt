package com.example.chit_chat.usecase

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class loginsignup(val context: Activity) {
    private val auth = Firebase.auth
    var isloggedin = false
    fun signupwithemail(email:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    isloggedin = true

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }

    }
    fun signinwithemail(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(context) { task ->
                if (task.isSuccessful) {
                    isloggedin = true
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)

                }
            }
    }
    fun signout(){
        Firebase.auth.signOut()
        isloggedin = false

    }
}

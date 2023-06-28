package com.example.chit_chat.utils

import android.app.Activity
import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.chit_chat.R
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class Passwordvisible @Inject constructor(val auth:FirebaseAuth) {

fun togglevisivblity(imageView: ImageView,editText: EditText,isvisible:Boolean){
        if (!isvisible){
           imageView.setImageResource(R.drawable.baseline_visibility_24)
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            //Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show()
        }
        else{
            imageView.setImageResource(R.drawable.baseline_visibility_off_24)
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        editText.let {
            it.setSelection(it.text.length)
        }


}


}
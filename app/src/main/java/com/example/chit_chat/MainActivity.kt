package com.example.chit_chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.chit_chat.usecase.loginsignup
import com.example.chit_chat.utils.dialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var loginsignup: loginsignup
    lateinit var dialog: dialog
    override fun onCreate(savedInstanceState: Bundle?) {


            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

//            dialog = dialog()
//
//            val dialogview = dialog.showPleaseWaitDialog(this@MainActivity)
//            loginsignup = loginsignup(this@MainActivity)
//        CoroutineScope(Dispatchers.Main).launch {
//            dialogview.show()
//            val job = CoroutineScope(Dispatchers.Main).launch {
//                loginsignup.signupwithemail("ayushkandpal@gmail.com","password")
//            }
//            job.join()
//           // dialogview.dismiss()
//            if (loginsignup.isloggedin){
//                Log.e("kaam hogya","kaam hogya")
//            }  }


        }



    }

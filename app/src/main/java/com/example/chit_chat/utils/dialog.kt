package com.example.chit_chat.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.chit_chat.R
import javax.inject.Inject

class dialog @Inject constructor(val context: Activity) {

    fun showPleaseWaitDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()

        return dialog
    }

}
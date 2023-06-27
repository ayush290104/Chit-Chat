package com.example.chit_chat.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.chit_chat.R

class dialog {

    fun showPleaseWaitDialog(context: Context): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()

        return dialog
    }

}
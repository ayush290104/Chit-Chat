package com.example.chit_chat.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.chit_chat.R
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class dialog @Inject constructor(val context: Activity) {
var s:String = ""

    fun showPleaseWaitDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()

        return dialog
    }
    fun showcalendar(textView: TextView){
        val mycalendar = Calendar.getInstance()
        val datepicker = DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
   mycalendar.set(Calendar.YEAR,year)
            mycalendar.set(Calendar.MONTH,month)
         mycalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
updateLabel(mycalendar, textView = textView)
        }
        DatePickerDialog(context,datepicker,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH))
            .show()
    }

  fun updateLabel(mycalendar: Calendar,textView: TextView) {
      val myformat = "dd-mm-yyyy"
      val sdf = SimpleDateFormat(myformat, Locale.UK)
      s = sdf.format(mycalendar.time)
      textView.text = "Your Date of Birth is $s"

    }

}
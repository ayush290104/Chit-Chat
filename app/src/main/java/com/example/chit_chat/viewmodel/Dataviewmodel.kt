package com.example.chit_chat.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chit_chat.models.Chat
import com.example.chit_chat.usecase.dataFirebase
import com.example.chit_chat.usecase.loginsignup
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataFirebase: dataFirebase
) : ViewModel() {

    private val listChat = MutableLiveData<ArrayList<Chat>>()
    val listChatLive: LiveData<ArrayList<Chat>>
        get() = listChat

    fun getChatList(uidBy: String, uidTo: String) {
        dataFirebase.getChatlist(uidBy, uidTo) { chatList ->
            Log.e("list of chat",chatList.toString())
            listChat.value = chatList
        }
    }
}

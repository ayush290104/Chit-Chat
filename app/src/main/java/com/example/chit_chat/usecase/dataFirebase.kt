package com.example.chit_chat.usecase

import android.net.Uri
import android.nfc.Tag
import android.util.Log
import com.example.chit_chat.models.Chat
import com.example.chit_chat.models.Request
import com.example.chit_chat.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.net.URL
import java.sql.Time
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class dataFirebase@Inject constructor() {
val database = Firebase.database
    val storagereference = Firebase.storage
val auth = Firebase.auth


    suspend fun addchat(uidby:String,uidto:String,text:String){
        val myref = database.getReference("Chats")
        val time = Date()
        try {
            myref.child(uidby+uidto).child(time.toString()).child("sent").setValue(text).await()
            myref.child(uidto+uidby).child(time.toString()).child("received").setValue(text).await()
        }catch (e:Exception){
            Log.e("error",e.message.toString())
        }
    }






    suspend fun addname(name: String){
       val myref = database.getReference("Users")
        try{
            myref.child(auth.currentUser!!.uid).child("Name").setValue(name).await()
            myref.child(auth.currentUser!!.uid).child("emailid").setValue(auth.currentUser!!.email).await()
            myref.child(auth.currentUser!!.uid).child("uid").setValue(auth.currentUser!!.uid).await()
        }catch (e:Exception){
            Log.e("exception",e.toString())
        }

    }
    suspend fun addrequest(uidto:String,uidby:String){
        val myref = database.getReference("Request")
        try{
            myref.child(uidto+uidby).child("requestcame").setValue(uidby).await()
            myref.child(uidto+uidby).child("requestsent").setValue(uidto).await()

        }catch (e:Exception){
            Log.e("exception",e.toString())
        }
    }
suspend fun adduri(uri: Uri){
     val myref = storagereference.getReference("images")
    val myref2 = database.getReference("Users")
    try{
        myref.child(auth.currentUser!!.uid).child("profile").putFile(uri).addOnSuccessListener {
            it.metadata?.reference?.downloadUrl?.addOnSuccessListener {url->
                myref2.child(auth.currentUser!!.uid).child("uri").setValue(url.toString())

            }
        }.await()



    }catch (e:Exception){
        Log.e("exception",e.toString())
    }
}
    suspend fun acceptrequest(uidby: String,uidto: String){

        val myref = database.getReference("Request")
        try{
            myref.child(uidto+uidby).child("status").setValue("accepted").await()


        }catch (e:Exception){
            Log.e("exception",e.toString())
        }

    }

 fun getChatlist(uidby:String,uidto:String,onResult: (ArrayList<Chat>) -> Unit){
    val myref = database.getReference("Chats").child(uidby+uidto)
    myref.addValueEventListener(object : ValueEventListener {


        override fun onDataChange(snapshot: DataSnapshot) {
            val ChatList = ArrayList<Chat>()
            if (snapshot.exists()) {
                for (datasnapshot in snapshot.children) {
                    if ((!ChatList.contains(datasnapshot.getValue(Chat::class.java)))) {
                        val Userss = datasnapshot.getValue(Chat::class.java)

                        if (Userss != null) {

                            ChatList.add(Userss)
                        }


                    }
                }
            }

            onResult(ChatList)

        }

        override fun onCancelled(error: DatabaseError) {
            onResult(ArrayList())
            Log.e("getAmbulanceList", error.toString())
        }
    })
}
suspend fun getrequest(onResult: (ArrayList<Request>) -> Unit){
    val myRef = database.getReference("Request")
    try {
        val snapshot = withContext(Dispatchers.IO) {
            myRef.get().await() // Fetch data from the database asynchronously
        }

        val userList = ArrayList<Request>()
        if (snapshot.exists()) {
            for (dataSnapshot in snapshot.children) {
                val request = dataSnapshot.getValue(Request::class.java)

                if (request != null) {
                    userList.add(request)
                }
            }
        }

        withContext(Dispatchers.Main) {
            onResult(userList) // Pass the user list to the callback function on the main thread
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            onResult(ArrayList()) // Pass an empty list to the callback function in case of an error
            Log.e("getUserList", e.toString())
        }
    }
}
  suspend fun getUserList(onResult: (ArrayList<User>)->Unit){
        val myRef = database.getReference("Users")
      try {
                val snapshot = withContext(Dispatchers.IO) {
                    myRef.get().await() // Fetch data from the database asynchronously
                }

                val userList = ArrayList<User>()
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(User::class.java)

                        if (user != null) {
                            userList.add(user)
                        }
                    }
                }

                withContext(Dispatchers.Main) {
                    onResult(userList) // Pass the user list to the callback function on the main thread
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(ArrayList()) // Pass an empty list to the callback function in case of an error
                    Log.e("getUserList", e.toString())
                }
            }


    }

}
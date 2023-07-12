package com.example.chit_chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.chit_chat.adapter.CustomAdapter
import com.example.chit_chat.databinding.FragmentFriendmakingBinding
import com.example.chit_chat.models.Request
import com.example.chit_chat.models.User
import com.example.chit_chat.usecase.dataFirebase
import com.example.chit_chat.usecase.loginsignup
import com.example.chit_chat.utils.dialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class friendmaking : Fragment() {
   lateinit var binding: FragmentFriendmakingBinding
   @Inject
   lateinit var dialog:dialog
   @Inject
   lateinit var loginsignup: loginsignup
@Inject
lateinit var dataFirebase: dataFirebase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentFriendmakingBinding.inflate(inflater,container,false)
var userlist = ArrayList<User>()
        var requestlist = ArrayList<Request>()

CoroutineScope(Dispatchers.Main).launch {
val dialogwait = dialog.showPleaseWaitDialog()
    dialogwait.show()
    val job2 = CoroutineScope(Dispatchers.Main).launch { dataFirebase.getrequest{
        for (user in it){

            if (user.requestcame==loginsignup.checkuser()?.uid||user.requestsent==loginsignup.checkuser()?.uid) {
                requestlist.add(user)
            }

        }
    }

    }
    job2.join()
    Log.e("list of request",requestlist.toString())
  val job = CoroutineScope(Dispatchers.Main).launch {

      dataFirebase.getUserList{
      for (user in it){
          if (user.uid!=loginsignup.checkuser()?.uid) {


              var count = 0
              for (request in requestlist) {

                  if (user.uid == request.requestsent||user.uid==request.requestcame) {
                      count = 1
                      break
                  }

              }
              if (count == 0) {
                  userlist.add(user)
              }
          }
      }
  }

  }
    job.join()
    dialogwait.dismiss()
    Log.e("List",userlist.toString())
    val customAdapter = CustomAdapter(userlist,activity)
    binding.recyclerview.adapter = customAdapter
    customAdapter.setOnButtonClicklistener(object:CustomAdapter.onButtonclicklistener{
        override fun onButtonclick(Position: Int) {
            Log.e("working it is","working it is")
            CoroutineScope(Dispatchers.Main).launch {
                val dialogplease = dialog.showPleaseWaitDialog()
                dialogplease.show()
                val job = CoroutineScope(Dispatchers.IO).launch{
                    dataFirebase.addrequest(userlist[Position].uid, loginsignup.checkuser()!!.uid)
                }
                job.join()
                dialogplease.dismiss()
            }

        }

    })
binding.recyclerview.layoutManager = LinearLayoutManager(activity)
}




        // Inflate the layout for this fragment
        return binding.root
    }



}
package com.example.chit_chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chit_chat.adapter.CustomAdapter2
import com.example.chit_chat.adapter.CustomAdapter3
import com.example.chit_chat.databinding.FragmentChatBinding
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
class chat : Fragment() {
    @Inject
    lateinit var dialog: dialog
    @Inject
    lateinit var loginsignup: loginsignup
    @Inject
    lateinit var dataFirebase: dataFirebase
lateinit var binding:FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        var userlist = ArrayList<User>()
        var requestlist = ArrayList<Request>()
        CoroutineScope(Dispatchers.Main).launch {
            val dialogwait = dialog.showPleaseWaitDialog()
            dialogwait.show()
            val job = CoroutineScope(Dispatchers.Main).launch { dataFirebase.getrequest{
                for (user in it){

                    if ((user.requestsent==loginsignup.checkuser()?.uid||user.requestcame==loginsignup.checkuser()?.uid)&& user.status=="accepted") {
                        requestlist.add(user)
                    }

                }
            }

            }
            job.join()

            val job2 = CoroutineScope(Dispatchers.Main).launch { dataFirebase.getUserList{
                for (user in it){
              if (user.uid!=loginsignup.checkuser()?.uid){
                  for (request in requestlist){
                      if (user.uid==request.requestcame||user.uid==request.requestsent){
                          userlist.add(user)
                      }
                  }
              }


                }
            }


            }
            job2.join()
            dialogwait.dismiss()
            val customAdapter = CustomAdapter3(userlist,activity)
            binding.recyclerView3.adapter = customAdapter
            customAdapter.setOnButtonClicklistener(object: CustomAdapter3.onButtonclicklistener{
                override fun onButtonclick(Position: Int) {
                    val args = Bundle()
                    args.putString("name",userlist[Position].Name)
                    args.putString("url",userlist[Position].uri)
                    args.putString("uid",userlist[Position].uid)
                    args.putString("email",userlist[Position].emailid)
                    Chatting().arguments = args
                    findNavController().navigate(R.id.action_mainFragment_to_chatting2,args)


                }

            })
            binding.recyclerView3.layoutManager = LinearLayoutManager(activity)
        }
        return binding.root
    }


}
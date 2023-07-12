package com.example.chit_chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chit_chat.adapter.CustomAdapter
import com.example.chit_chat.adapter.CustomAdapter2
import com.example.chit_chat.databinding.FragmentProfileBinding
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
class profile : Fragment() {
    @Inject
    lateinit var dialog: dialog
    @Inject
    lateinit var loginsignup: loginsignup
    @Inject
    lateinit var dataFirebase: dataFirebase

lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        var userlist = ArrayList<User>()
        var requestlist = ArrayList<Request>()
        CoroutineScope(Dispatchers.Main).launch {
            val dialogwait = dialog.showPleaseWaitDialog()
            dialogwait.show()
            val job = CoroutineScope(Dispatchers.Main).launch { dataFirebase.getrequest{
                for (user in it){

                    if (user.requestsent==loginsignup.checkuser()?.uid&& user.status=="NotAccepted") {
                              requestlist.add(user)
                    }

                }
            }

            }
            job.join()

            val job2 = CoroutineScope(Dispatchers.Main).launch { dataFirebase.getUserList{
                for (user in it){

                   for (request in requestlist){
                       if (user.uid==request.requestcame){
                           userlist.add(user)
                       }
                   }

                }
            }


            }
            job2.join()
            dialogwait.dismiss()
            val customAdapter = CustomAdapter2(userlist,activity)
            binding.recyclerView2.adapter = customAdapter
            customAdapter.setOnButtonClicklistener(object: CustomAdapter2.onButtonclicklistener{
                override fun onButtonclick(Position: Int) {
                    Log.e("working it is","working it is")
                    CoroutineScope(Dispatchers.Main).launch {
                        val dialogplease = dialog.showPleaseWaitDialog()
                        dialogplease.show()
                        val job = CoroutineScope(Dispatchers.IO).launch{
                            dataFirebase.acceptrequest(userlist[Position].uid, loginsignup.checkuser()!!.uid)
                        }
                        job.join()
                        dialogplease.dismiss()
                    }

                }

            })
            binding.recyclerView2.layoutManager = LinearLayoutManager(activity)
        }





        return binding.root
    }

}
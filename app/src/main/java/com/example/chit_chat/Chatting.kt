package com.example.chit_chat

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.chit_chat.adapter.CustomAdapter4
import com.example.chit_chat.databinding.FragmentChatBinding
import com.example.chit_chat.databinding.FragmentChattingBinding
import com.example.chit_chat.models.Chat
import com.example.chit_chat.usecase.dataFirebase
import com.example.chit_chat.usecase.loginsignup
import com.example.chit_chat.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Chatting : Fragment() {
    lateinit var binding: FragmentChattingBinding

    @Inject
    lateinit var dataFirebase: dataFirebase
    @Inject
    lateinit var loginsignup: loginsignup
    val viewModel:DataViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        binding = FragmentChattingBinding.inflate(inflater,container,false)
        val value1 = bundle?.getString("name")?:""
        val value2 = bundle?.getString("url")?:""
        val value3 = bundle?.getString("uid")?:""
        val value4 = bundle?.getString("email")?:""
        // Inflate the layout for this fragment
        binding.friendname.text = value1
        binding.friendemail.text = value4
        try {
            Glide.with(this).load(value2).into(binding.friendimage)
        }catch (e:Exception){
            Toast.makeText(activity,e.message,Toast.LENGTH_SHORT).show()
        }
        viewModel.getChatList(loginsignup.checkuser()!!.uid,value3)


            binding.sendbutton.setOnClickListener{
                if(binding.textsend.text.isNotBlank()){


                CoroutineScope(Dispatchers.IO).launch{
                    dataFirebase.addchat(loginsignup.checkuser()!!.uid,value3,binding.textsend.text.toString())
                }
                    binding.textsend.setText("")


            }
            }


        viewModel.listChatLive.observe(this){
            Toast.makeText(activity,it.toString(),Toast.LENGTH_SHORT).show()
            binding.chatrecycleview.adapter =CustomAdapter4(it)

        }

        binding.chatrecycleview.layoutManager = LinearLayoutManager(activity)




        return binding.root
    }








}
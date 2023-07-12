package com.example.chit_chat

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.FragmentMainBinding
import com.example.chit_chat.usecase.dataFirebase
import com.google.android.gms.dynamic.SupportFragmentWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
lateinit var binding:FragmentMainBinding
@Inject
lateinit var dataFirebase: dataFirebase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater,container,false)


        binding.bottomNavigationView.setOnItemSelectedListener {
    when(it.itemId){
        R.id.chatting->replace(chat())
        R.id.friends->replace(friendmaking())
R.id.profileuser->replace(profile())


        else ->{


        }



    }
    true
}







        return binding.root
    }
private fun replace(fragment: Fragment){
    val manager = childFragmentManager
   val fragementtransaction =  manager.beginTransaction()
    fragementtransaction.replace(R.id.frame_layout,fragment)
    fragementtransaction.commit()


}


}
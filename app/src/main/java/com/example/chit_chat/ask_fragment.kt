package com.example.chit_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.AskFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ask_fragment : Fragment() {
   lateinit var binding:AskFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AskFragmentBinding.inflate(inflater,container,false)
    binding.newuser.setOnClickListener {
        findNavController().navigate(R.id.action_ask_fragment_to_signup)
    }
    binding.Alreadyuser.setOnClickListener{
        findNavController().navigate(R.id.action_ask_fragment_to_login)
    }


        // Inflate the layout for this fragment
        return binding.root
    }



}
package com.example.chit_chat

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.ActivityMainBinding
import com.example.chit_chat.databinding.FragmentSplashBinding
import com.example.chit_chat.usecase.loginsignup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class Splash_fragment : Fragment() {

lateinit var binding: FragmentSplashBinding
@Inject
lateinit var loginsignup: loginsignup
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        val nc = findNavController()

if(loginsignup.checkuser()!=null){
    if (loginsignup.checkuser()!!.isEmailVerified){
        binding.getstarted.visibility = View.INVISIBLE
        Handler(Looper.myLooper()!!).postDelayed({
            nc.navigate(R.id.action_splash_fragment_to_mainFragment)
        }, 2000)

    }
    else{
    binding.getstarted.visibility = View.INVISIBLE
    Handler(Looper.myLooper()!!).postDelayed({
        nc.navigate(R.id.action_splash_fragment_to_login)
    }, 2000)
}
}
        else{
            binding.getstarted.setOnClickListener{
                nc.navigate(R.id.action_splash_fragment_to_ask_fragment)
            }
        }
        // Inflate the layout for this fragment




        return binding.root
    }


}
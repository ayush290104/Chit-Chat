package com.example.chit_chat

import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.ActivityMainBinding
import com.example.chit_chat.databinding.FragmentSplashBinding
import com.example.chit_chat.usecase.loginsignup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
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
    binding.button.visibility = View.INVISIBLE
    Handler(Looper.myLooper()!!).postDelayed({
        nc.navigate(R.id.action_splash_fragment_to_ask_fragment)
    }, 20000)
}
        else{
            binding.button.setOnClickListener{
                nc.navigate(R.id.action_splash_fragment_to_ask_fragment)
            }
        }
        // Inflate the layout for this fragment




        return binding.root
    }


}
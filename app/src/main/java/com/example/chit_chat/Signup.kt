package com.example.chit_chat

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.FragmentSignupBinding
import com.example.chit_chat.usecase.loginsignup
import com.example.chit_chat.utils.Passwordvisible
import com.example.chit_chat.utils.dialog


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class Signup : Fragment() {
    @Inject
    lateinit var passwordvisible: Passwordvisible
    private lateinit var binding: FragmentSignupBinding
    @Inject
    lateinit var dialog: dialog
    @Inject
    lateinit var loginsignup:loginsignup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var isvisible = false
        var isvisible2 = false

        binding = FragmentSignupBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.imageView.setOnClickListener{
          passwordvisible.togglevisivblity(imageView = binding.imageView,binding.editTextTextPassword,isvisible)
            isvisible = !isvisible
            }
        binding.imageView2.setOnClickListener{
            passwordvisible.togglevisivblity(imageView = binding.imageView2,binding.editTextTextPassword2,isvisible2)
            isvisible2 = !isvisible2
        }

     binding.button4.setOnClickListener {
         if (binding.editTextTextPassword.text.toString()!=binding.editTextTextPassword2.text.toString()){
             Toast.makeText(activity,"confirm password does not match",Toast.LENGTH_SHORT).show()
         }
         else if(!binding.editTextTextEmailAddress.text.toString().contains("@")){
             Toast.makeText(activity,"Enter valid email address",Toast.LENGTH_SHORT).show()
         }

         else{
           val dialog =  dialog.showPleaseWaitDialog()
          CoroutineScope(Dispatchers.Main).launch {
              dialog.show()

            val job = CoroutineScope(Dispatchers.Main).async {
                loginsignup.signupwithemail(binding.editTextTextEmailAddress.text.toString(),binding.editTextTextPassword.text.toString())

            }
             job.await()
              dialog.dismiss()
            delay(300)
              if(loginsignup.checkuser()!=null){
                  findNavController().navigate(R.id.action_signup_to_info2)
              }



          }

         }
     }



        return binding.root
    }


}
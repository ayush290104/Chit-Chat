package com.example.chit_chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.FragmentLoginBinding
import com.example.chit_chat.usecase.loginsignup
import com.example.chit_chat.utils.Passwordvisible
import com.example.chit_chat.utils.dialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class login : Fragment() {
   lateinit var binding:FragmentLoginBinding
   @Inject
    lateinit var passwordvisible: Passwordvisible
    @Inject
    lateinit var loginsignup: loginsignup
    @Inject
    lateinit var dialog: dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var isvisible = false
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding.visible.setOnClickListener{
            passwordvisible.togglevisivblity(imageView = binding.visible,binding.passwordlogin,isvisible)
            isvisible = !isvisible
        }
binding.login.setOnClickListener {
    val d = dialog.showPleaseWaitDialog()
    if(!binding.EmailAddresslogin.text.toString().contains("@")){
        Toast.makeText(activity,"Enter valid email address", Toast.LENGTH_SHORT).show()
    }
    else{
        CoroutineScope(Dispatchers.Main).launch {
            d.show()
  val job = CoroutineScope(Dispatchers.Main).launch {
      loginsignup.signinwithemail(binding.EmailAddresslogin.text.toString(),binding.passwordlogin.text.toString())
  }
job.join()
  d.dismiss()
  if (loginsignup.checkuser()!=null){
      if (loginsignup.checkuser()!!.isEmailVerified){
          findNavController().navigate(R.id.action_login_to_mainFragment)
      }
      else{
          Toast.makeText(activity,"Please Verify your email and try again",Toast.LENGTH_SHORT).show()
      }
  }

        }
    }
}
binding.getverification.setOnClickListener {
    if (loginsignup.checkuser()!=null){
        loginsignup.checkuser()!!.sendEmailVerification()
    }
}



        return binding.root
    }


}
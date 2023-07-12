package com.example.chit_chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.chit_chat.databinding.FragmentInfoBinding
import com.example.chit_chat.usecase.dataFirebase
import com.example.chit_chat.usecase.loginsignup
import com.example.chit_chat.utils.dialog
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.Calendar
import javax.inject.Inject
@AndroidEntryPoint
class Info : Fragment() {
    lateinit var binding: FragmentInfoBinding
    @Inject
    lateinit var dialog: dialog
    var uri:Uri? = null
@Inject
lateinit var loginsignup: loginsignup
@Inject
lateinit var dataFirebase: dataFirebase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        binding.imageViewcalendar.setOnClickListener{
            dialog.showcalendar(binding.selectdob)

        }
        binding.imageprofile.setOnClickListener{


                CoroutineScope(Dispatchers.Main).launch {

                    ImagePicker.with(this@Info)
                        .crop()
                        .compress(524)         //Final image size will be less than 1 MB(Optional)
                        //Final image resolution will be less than 1080 x 1080(Optional)
                        .createIntent { intent ->
                            startForProfileImageResult.launch(intent)
                        }
                }




        }
        binding.continu.setOnClickListener {

            if (binding.editname.text.isBlank()&& binding.selectdob.text.toString().contains("Select")){
                Toast.makeText(activity,"Field Cant be empty",Toast.LENGTH_SHORT).show()
            }

            else {
                val s = binding.selectdob.text.toString().subSequence(binding.selectdob.text.toString().length-4,binding.selectdob.text.toString().length).toString()
               val a = s.toInt()
                if (a>2004){
                    Toast.makeText(activity,"You are too young",Toast.LENGTH_SHORT).show()
                }
else{
    Toast.makeText(activity,binding.selectdob.text.toString().subSequence(binding.selectdob.text.toString().length-4,binding.selectdob.text.toString().length).toString(),Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.Main).launch {

                        val dialog = dialog.showPleaseWaitDialog()
                        dialog.show()
                        val job = GlobalScope.launch {
                            uri?.let { it1 ->
                                loginsignup.updateuser(binding.editname.text.toString(),
                                    it1
                                )
                                dataFirebase.adduri(it1)

                            }
                            dataFirebase.addname(binding.editname.text.toString())

                        }
                        job.join()
                        dialog.dismiss()
                        delay(300)
                        findNavController().navigate(R.id.action_info2_to_login)




                    }
}



            }
        }





        return binding.root
    }
   private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                uri = data.data
                binding.imageprofile.setImageURI(fileUri)
                binding.textViewpick.visibility = View.GONE
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


}
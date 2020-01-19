package com.example.sms_autotransapp.Contact_Log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.roomexample_yena.ContactLogViewModel
import com.example.roomexample_yena.ContactViewModel
import com.example.sms_autotransapp.R

class ContactLogActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var contactLogViewModel : ContactLogViewModel
    private var id: Long? = null
    override fun getIntent(): Intent {

        return super.getIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        val receivesender = intent?.getStringExtra("sender")
        val contents = intent?.getStringExtra("contents")
        val receivedDate = intent?.getStringExtra("receivedDate")

        Log.d("LogA","onNewIntent :"+receivesender +" : "+contents+" : "+receivedDate)
        if("01046973907" == receivesender){
            Log.d("LogA","onNewIntent Stringcheck :"+receivesender )
        }
        val contacts  = contactViewModel.getAll().value
        contacts?.forEach { contact -> Unit
            //if(contact.receiveNumber.trim() == receivesender){
                Log.d("LogA","onNewIntent namecheck :"+receivesender +" :  ${contact.receiveName.toString()}")
            //}
        }
        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_log)

        val receivesender = intent?.getStringExtra("sender")
        val contents = intent?.getStringExtra("contents")
        val receivedDate = intent?.getStringExtra("receivedDate")
        Log.d("LogA","onCreate :" +receivesender +" : "+contents+" : "+receivedDate)
        if("01046973907" == receivesender){
            Log.d("LogA","onCreate Stringcheck :"+receivesender )
        }
       /*
        val contacts  = contactViewModel.getAll().value
        contacts?.forEach { contact -> Unit
            //if(contact.receiveNumber.trim() == receivesender){
                Log.d("LogA","onCreate namecheck :"+receivesender +" :  ${contact.receiveName.toString()}")
            //}
        }
*/

    }
}

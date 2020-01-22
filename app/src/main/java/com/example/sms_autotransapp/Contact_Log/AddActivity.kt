package com.example.sms_autotransapp.Contact_Log

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.roomexample_yena.ContactLog
import com.example.roomexample_yena.ContactLogViewModel
import com.example.roomexample_yena.ContactViewModel
import com.example.sms_autotransapp.R

class AddActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var contactLogViewModel : ContactLogViewModel
    private var id: Long? = null
    private val TAG = "AddActivity"
    private var receivesender :String = ""
    private var contents :String = ""
    private var receivedDate :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        contactViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(ContactViewModel::class.java)
        contactLogViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(ContactLogViewModel::class.java)
        val contacts  = contactViewModel.getAll().value

        // intent null check & get extras
        if(intent !== null && intent.hasExtra(EXTRA_CONTACT_NUMBER) && intent.hasExtra(EXTRA_CONTENTS) && intent.hasExtra(EXTRA_RECEIVED_DATE)
            && intent.hasExtra(EXTRA_CONTACT_ID))
            {
                Log.d(TAG,"start")
                id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
                //get intent data
                receivesender = intent.getStringExtra(EXTRA_CONTACT_NUMBER)
                contents = intent.getStringExtra(EXTRA_CONTENTS)
                receivedDate = intent.getStringExtra(EXTRA_RECEIVED_DATE)

                contacts?.forEach { contact -> Unit
                    val receiveNumber = contact.receiveNumber.replace("-", "")

                    if (receiveNumber == receivesender) {
                        val receiveName = contact.receiveName
                        val receiveTime = receivedDate
                        val receiveNumber = contact.receiveNumber
                        val message = contents
                        val transNumber = contact.transNumber
                        val transName = contact.transName
                        val transTime = receivedDate
                        contactLogViewModel.insert(ContactLog(id,
                                receiveName,
                                receiveTime,
                                receiveNumber,
                                message,
                                transName,
                                transTime,
                                transNumber))}
                    Log.d(TAG,"end")
                    }



                finish()
            }









    }

    companion object {
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTENTS = "EXTRA_CONTENTS"
        const val EXTRA_RECEIVED_DATE = "EXTRA_RECEIVED_DATE"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"


    }
}

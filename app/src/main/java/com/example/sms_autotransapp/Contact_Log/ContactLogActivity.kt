package com.example.sms_autotransapp.Contact_Log

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample_yena.*
import com.example.sms_autotransapp.R
import com.example.sms_autotransapp.UserAPI
import kotlinx.android.synthetic.main.activity_contact_log.*
import kotlinx.android.synthetic.main.activity_contact_register.*
import kotlinx.android.synthetic.main.sub_contact_register_view.*

class ContactLogActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var contactLogViewModel : ContactLogViewModel
    private var id: Long? = null
    private val TAG = "ContactLogActivity"
    private var receivesender : String = ""
    private var contents : String = ""
    private var receivedDate : String = ""

    override fun onNewIntent(intent: Intent) {

        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_log)
        val user = UserAPI(this)

        Log.d(TAG,"Line1 onCreate")

        // Set contactItemClick & contactItemLongClick lambda
        val adapter = ContactLogAdapter({ contactLog ->
            Log.d(TAG,"Line2 start adpater")
            id = contactLog.id
            // intent null check & get extras
            if(intent != null && intent.hasExtra(EXTRA_BROD_NUMBER) && intent.hasExtra(EXTRA_BROD_CONTENTS)
                && intent.hasExtra(EXTRA_BROD_RECEIVED_DATE)) {
                Log.d(TAG,"Line3 intent get start ")
                receivesender = intent.getStringExtra(EXTRA_BROD_NUMBER)
                contents = intent.getStringExtra(EXTRA_BROD_CONTENTS)
                receivedDate = intent.getStringExtra(EXTRA_BROD_RECEIVED_DATE)

                val intent = Intent(this, AddActivity::class.java)
                intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, receivesender)
                intent.putExtra(AddActivity.EXTRA_CONTENTS, contents)
                intent.putExtra(AddActivity.EXTRA_RECEIVED_DATE, receivedDate)

                intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contactLog.id)
                startActivity(intent)
                Log.d(TAG,"Line4 start addActivity ")
            }


        }, { contactLog ->
            deleteDialog(contactLog)
        })

        val lm = LinearLayoutManager(this)
        contact_log_list.adapter = adapter
        contact_log_list.layoutManager = lm
        contact_log_list.setHasFixedSize(true)


        contactLogViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(ContactLogViewModel::class.java)
        contactLogViewModel.getAll().observe(this, Observer<List<ContactLog>>{ contactsLog ->
            adapter.setContacts(contactsLog!!)
        })




    }

    private fun deleteDialog(contactLog: ContactLog) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contactLog?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactLogViewModel.delete(contactLog)
            }
        builder.show()
    }
/*
    private fun contactLog(getintent : Intent?,intentType :String){

        val receivesender = getintent?.getStringExtra("sender")
        val contents = getintent?.getStringExtra("contents")
        val receivedDate = getintent?.getStringExtra("receivedDate")

        val contacts  = contactViewModel.getAll().value

        contacts?.forEach { contact -> Unit
            val receiveNumber = contact.receiveNumber.replace("-", "")
            if (receiveNumber == receivesender){
                val receiveName = contact.receiveName
                val receiveTime = receivedDate
                val receiveNumber = contact.receiveNumber
                val message   = contents
                val transNumber = contact.transNumber
                val transName = contact.transName
                val transTime   = receivedDate
                contactLogViewModel.insert(
                    ContactLog(
                    id,receiveName,receiveTime,receiveNumber,message,transName,transTime,transNumber))
                Log.d(TAG,"[${intentType}: ] receiveName: ${receiveName}" +
                        " receiveTime : ${receiveTime}" +
                        " receiveNumber : ${receiveNumber}" +
                        " message : ${message}" +
                        " transNumber : ${transNumber}" +
                        " transName : ${transName}" +
                        " transTime : ${transTime}"
                )
            }

        }


    }
    //contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
    //contactLogViewModel = ViewModelProviders.of(this).get(ContactLogViewModel::class.java)
  */
    companion object {
        const val EXTRA_BROD_NUMBER = "EXTRA_BROD_NUMBER"
        const val EXTRA_BROD_CONTENTS = "EXTRA_BROD_CONTENTS"
        const val EXTRA_BROD_RECEIVED_DATE = "EXTRA_BROD_RECEIVED_DATE"



    }



}

package com.example.sms_autotransapp.Contact_Log

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample_yena.*
import com.example.sms_autotransapp.R
import com.example.sms_autotransapp.UserAPI
import kotlinx.android.synthetic.main.activity_contact_log.*
import kotlinx.android.synthetic.main.activity_contact_register.*

class ContactLogActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var contactLogViewModel : ContactLogViewModel
    private var id: Long? = null
    private val TAG = "ContactLogActivity"


    override fun onNewIntent(intent: Intent) {

        contactLog(intent, "onNewIntent")

        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_log)
        val user = UserAPI(this)
      //  val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      //  inflater.inflate(R.layout.sub_contact_log_view,contact_log_list,true)
        val intent = getIntent()
        contactLog(intent, "onCreateIntent")


        // Set contactItemClick & contactItemLongClick lambda
        val adapter = ContactLogAdapter({ contactLog ->
            id = contactLog.id

        }, { contactLog ->
            deleteDialog(contactLog)
        })

        val lm = LinearLayoutManager(this)
        contact_log_list.adapter = adapter
        contact_log_list.layoutManager = lm
        contact_log_list.setHasFixedSize(true)

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)


        contactLogViewModel = ViewModelProviders.of(this).get(ContactLogViewModel::class.java)
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
            } else {
                Log.d(TAG,"[${intentType}: ] receivesender: ${receivesender}" +
                        " contents : ${contents}" +
                        " receiveNumber : ${receiveNumber}" +
                        " receivedDate : ${receivedDate}")
            }

        }


    }




}

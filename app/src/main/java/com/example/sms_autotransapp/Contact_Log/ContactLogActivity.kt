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
import androidx.room.Room
import com.example.roomexample_yena.*
import com.example.sms_autotransapp.R
import com.example.sms_autotransapp.UserAPI
import kotlinx.android.synthetic.main.activity_contact_log.*
import kotlinx.android.synthetic.main.activity_contact_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ContactLogActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var contactLogViewModel : ContactLogViewModel
    private var id: Long? = null
    private val TAG = "sss"

    override fun onStart() {
        super.onStart()


    }

    override fun onResume() {
        super.onResume()


    }

    override fun onNewIntent(intent: Intent) {

        contactLog(intent, "onNewIntent" )
        Log.d(TAG,"onNewIntent")
        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_log)
        val user = UserAPI(this)


        // Set contactItemClick & contactItemLongClick lambda
        val adapter = ContactLogAdapter({ contactLog ->
            id = contactLog.id

        }, { contactLog ->
            deleteLogDialog(contactLog)
        })

        val lm = LinearLayoutManager(this)
        contact_log_list.adapter = adapter
        contact_log_list.layoutManager = lm
        contact_log_list.setHasFixedSize(true)


        // Set contactItemClick & contactItemLongClick lambda
        val adapter2 = ContactAdapter({ contact ->
            id = contact.id

            // put extras of contact info & start AddActivity
/*
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
*/

        }, { contact ->
            deleteDialog(contact)

        })
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>>{contacts ->
            adapter2.setContacts(contacts!!)
        })


        contactLogViewModel = ViewModelProviders.of(this).get(ContactLogViewModel::class.java)
        contactLogViewModel.getAll().observe(this, Observer<List<ContactLog>>{ contactsLog ->
            adapter.setContacts(contactsLog!!)
        })
/*
        val contacts  = contactViewModel.getAll().value

        contacts?.forEach { contact ->
            Unit
            Log.d(
                TAG, "inside onCreate"

            )
            val receiveNumber = contact.receiveNumber.replace("-", "")
            Log.d(
                TAG, "inside contacts number ${receiveNumber}"

            )
        }

*/
        if(intent.hasExtra("sender")) {
            Log.d(TAG, "Sender  sender")
            main()

        }

        button.setOnClickListener({
            main()


        })



    }
    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contactLog?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
    private fun deleteLogDialog(contactLog: ContactLog) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contactLog?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactLogViewModel.delete(contactLog)
            }
        builder.show()
    }



    private fun contactLog(getintent : Intent,intentType :String){

        val receivesender = getintent.getStringExtra("sender")
        val contents = getintent.getStringExtra("contents")
        val receivedDate = getintent.getStringExtra("receivedDate")
        Log.d(TAG,"${intentType}"
            + " sender : ${receivesender}"
            + " contents : ${contents}"
            + " receivedDate : ${receivedDate}"

        )
         val contacts  : List<Contact> ?= contactViewModel.getAll().value
         contacts?.forEach { contact -> Unit
            Log.d( TAG, "inside contacts"    )
            val receiveNumber = contact.receiveNumber.replace("-", "")
            Log.d(
                TAG, "inside contacts number ${receiveNumber}"

            )
            if (receiveNumber == receivesender) {
                val receiveName = contact.receiveName
                val receiveTime = receivedDate
                val receiveNumber = contact.receiveNumber
                val message = contents
                val transNumber = contact.transNumber
                val transName = contact.transName
                val transTime = receivedDate
                val contactlog = ContactLog(
                    id,
                    receiveName,
                    receiveTime,
                    receiveNumber,
                    message,
                    transName,
                    transTime,
                    transNumber
                )

                contactLogViewModel.insert(contactlog)

                Log.d(
                    TAG, "[inside ${intentType}: ] receiveName: ${receiveName}" +
                            " receiveTime : ${receiveTime}" +
                            " receiveNumber : ${receiveNumber}" +
                            " message : ${message}" +
                            " transNumber : ${transNumber}" +
                            " transName : ${transName}" +
                            " transTime : ${transTime}"
                )
            } else {
                Log.d(
                    TAG, "[${intentType}: ] receivesender: ${receivesender}" +
                            " contents : ${contents}" +
                            " receiveNumber : ${receiveNumber}" +
                            " receivedDate : ${receivedDate}"
                )
            }

        }



    }
    fun main() = runBlocking {
        launch {
            contactLog(intent, "getIntent")
            delay(1000L)
            Log.d(TAG, "Corutine scope")
            contactLog(intent, "getIntent")

        }
    }




}

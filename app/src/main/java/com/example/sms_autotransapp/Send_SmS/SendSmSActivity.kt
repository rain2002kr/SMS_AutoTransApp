package com.example.sms_autotransapp.Send_SmS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomexample_yena.*
import com.example.sms_autotransapp.R
import com.example.sms_autotransapp.UserAPI
import kotlinx.android.synthetic.main.activity_contact_register.*
import kotlinx.android.synthetic.main.activity_send_sm_s.*

class SendSmSActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private lateinit var logViewModel: LogViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sm_s)

        val user = UserAPI(this)



        check.setOnClickListener({
            //val logs  = logViewModel.getAll().value

            val sss = edTest.text.toString()
            //val log = Log(id, sss)

            val contacts = contactViewModel.getAll().value

            contacts?.forEach { contact -> Unit
                val reNum = contact.receiveNumber.replace("-","")
                println(contact.receiveNumber + " : " + reNum)
                println(contact.receiveName)

                if(sss == contact.receiveNumber){
                    println("번호가같다" + contact.receiveNumber)
                }
                if(sss == reNum){
                    println("reNum 번호가같다" + reNum)
                }

            }


            //logViewModel.insert(log)
        })

        logDelbt.setOnClickListener({
            val logs  = logViewModel.getAll().value?.last()
            logViewModel.delete(logs!!)
        })

        val adapter = LogAdapter({ log ->
            id = log.id
        }, { log ->
            deleteDialog(log)
        })

        val lm = LinearLayoutManager(this)
        logTest_view.adapter = adapter
        logTest_view.layoutManager = lm
        logTest_view.setHasFixedSize(true)



        logViewModel = ViewModelProviders.of(this).get(LogViewModel::class.java)
        logViewModel.getAll().observe(this, Observer<List<Log>>{ logs ->
            adapter.setContacts(logs!!)
        })


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

        }, {

        })


        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>>{contacts ->
            adapter2.setContacts(contacts!!)
        })


    }
    private fun deleteDialog(log: Log) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                logViewModel.delete(log)
            }
        builder.show()
    }

    private fun println(data:String){
        txtprint.append(data + "\n")
    }

}

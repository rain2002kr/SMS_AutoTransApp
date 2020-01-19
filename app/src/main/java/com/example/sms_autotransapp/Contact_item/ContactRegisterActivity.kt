package com.example.sms_autotransapp.Contact_item

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
import com.example.roomexample_yena.Contact
import com.example.roomexample_yena.ContactAdapter
import com.example.roomexample_yena.ContactViewModel
import com.example.sms_autotransapp.R
import kotlinx.android.synthetic.main.activity_contact_register.*
import kotlinx.android.synthetic.main.sub_contact_register_view.*

class ContactRegisterActivity : AppCompatActivity() {
    private lateinit var contactViewModel : ContactViewModel
    private var id: Long? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_register)

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.sub_contact_register_view,contact_register,true)

        btinsert.setOnClickListener({
            val receNumber = txtSetReceNumber.text.toString()
            val receName = txtSetReceName.text.toString()
            val tranNumber = txtSetTransNumber.text.toString()
            val tranName = txtSetTransName.text.toString()

            val initial = receName[0].toUpperCase()
            val contact = Contact(id,R.drawable.receive_sms!!,receNumber,receName,
                R.drawable.exchange!!,R.drawable.send_sms!!,tranNumber,tranName,initial)

            contactViewModel.insert(contact)

            val contacts  = contactViewModel.getAll().value
            Log.d("sss","${contact.id.toString()} contacts size ${contacts?.size.toString()}")

        })

        btdelete.setOnClickListener({

            val contact  = contactViewModel.getAll().value.orEmpty().last()
            val contacts  = contactViewModel.getAll().value

            Log.d("sss","${contact.id.toString()} contacts size ${contacts?.size.toString()}")
            Log.d("sss","${contact.id.toString()} contact index ${contacts?.lastIndex}")

            if(contacts?.lastIndex!! > 0 ) {
                contactViewModel.delete(contact)
                Log.d("sss","${contact.id.toString()} contacts After size ${contacts?.size.toString()}")
            }

        })
        // Set contactItemClick & contactItemLongClick lambda
        val adapter = ContactAdapter({ contact ->
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

        val lm = LinearLayoutManager(this)
        contact_list.adapter = adapter
        contact_list.layoutManager = lm
        contact_list.setHasFixedSize(true)



        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>>{contacts ->
            adapter.setContacts(contacts!!)
        })


    }
    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }

}

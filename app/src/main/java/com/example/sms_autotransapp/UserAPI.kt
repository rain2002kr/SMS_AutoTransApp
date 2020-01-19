package com.example.sms_autotransapp

import android.content.Context
import android.widget.Toast

class UserAPI(val context: Context) {
    fun toast(data: String) {
        Toast.makeText(context, "data" , Toast.LENGTH_LONG).show()
    }



}
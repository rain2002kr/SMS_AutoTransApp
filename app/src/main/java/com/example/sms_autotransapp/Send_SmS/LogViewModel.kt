package com.example.roomexample_yena

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class LogViewModel (application: Application) : AndroidViewModel(application) {

    private val reposityory = LogReposityory(application)
    private val logs = reposityory.getAll()

    fun getAll() : LiveData<List<Log>> {
        return this.logs
    }

    fun insert(log :Log){
        reposityory.insert(log)
    }

    fun delete(log : Log){
        reposityory.delete(log)
    }

    fun update(log :  Log){
        reposityory.update(log)
    }

}
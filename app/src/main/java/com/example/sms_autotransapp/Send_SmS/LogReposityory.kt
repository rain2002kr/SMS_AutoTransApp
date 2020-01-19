package com.example.roomexample_yena

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class LogReposityory (application: Application) {

    private val logDatabase = LogDatabase.getInstance(application)!!
    private val logDao : LogDao = logDatabase.logDao()
    private val logs : LiveData<List<Log>> = logDao.getAll()

    fun getAll() : LiveData<List<Log>> {
        return logs
    }

    fun insert(log : Log){
        try {
            val thread = Thread(Runnable {
                logDao.insert(log)
            })
            thread.start()
        } catch (e : Exception){e.printStackTrace()}
    }

    fun delete(log: Log) {
        try {
            val thread = Thread(Runnable {
                logDao.delete(log)
            })
            thread.start()
        } catch (e : Exception){e.printStackTrace()}

    }
    fun update(log: Log) {
        try {
            val thread = Thread(Runnable {
                logDao.update(log)
            })
            thread.start()
        } catch (e : Exception){e.printStackTrace()}

    }


}
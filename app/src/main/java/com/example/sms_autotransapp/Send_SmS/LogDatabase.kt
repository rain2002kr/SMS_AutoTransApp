package com.example.roomexample_yena

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Log::class], version = 1)
abstract class LogDatabase : RoomDatabase() {

    abstract fun logDao() : LogDao

    companion object {
        private var INSTANCE : LogDatabase? = null

        fun getInstance(context : Context): LogDatabase? {
            if(INSTANCE == null){
              synchronized(LogDatabase::class){
                  INSTANCE = Room.databaseBuilder(context.applicationContext,
                      LogDatabase::class.java, "Log")
                      .fallbackToDestructiveMigration()
                      .build()
              }
            }
            return INSTANCE
        }

    }


}
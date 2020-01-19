package com.example.roomexample_yena

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LogDao{
    @Query("SELECT * FROM logTest ORDER BY id ASC ")
    fun getAll() : LiveData<List<Log>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(log :Log)

    @Delete
    fun delete(log :Log)

    @Update
    fun update(log :Log)

}
package com.example.eisenhower.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eisenhower.model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM zadania WHERE czyWykonane = 0")
    fun getNieWykonaneZadania(): LiveData<List<Task>>

    @Query("SELECT * FROM zadania WHERE czyWykonane = 1")
    fun getWykonaneZadania(): LiveData<List<Task>>

    @Insert
    fun insert(zadanie: Task)

    @Update
    fun update(zadanie: Task)

    @Delete
    fun delete(zadanie: Task)
}

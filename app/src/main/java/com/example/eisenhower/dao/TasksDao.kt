package com.example.eisenhower.dao

import androidx.room.*
import com.example.eisenhower.model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM zadania WHERE czyWykonane = 0")
    fun getNieWykonaneZadania(): List<Task>

    @Query("SELECT * FROM zadania WHERE czyWykonane = 1")
    fun getWykonaneZadania(): List<Task>

    @Insert
    fun insert(zadanie: Task)

    @Update
    fun update(zadanie: Task)

    @Delete
    fun delete(zadanie: Task)
}

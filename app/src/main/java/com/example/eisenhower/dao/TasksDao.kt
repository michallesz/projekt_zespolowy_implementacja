package com.example.eisenhower.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eisenhower.model.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks WHERE isDone = 0 ORDER BY date ASC")
    fun getUndoneTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE isDone = 1 ORDER BY date ASC")
    fun getDoneTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}

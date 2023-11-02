package com.example.eisenhower.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.eisenhower.dao.TasksDao
import com.example.eisenhower.database.AppDatabase
import com.example.eisenhower.model.Task


class TaskRepository internal constructor(application: Application) {
    private val mTaskDao: TasksDao
    private val mAllUnfinishedTasks: LiveData<List<Task>>
    private val mAllFinishedTasks: LiveData<List<Task>>

    init {
        val elementRoomDatabase: AppDatabase = AppDatabase.getDatabase(application)
        mTaskDao = elementRoomDatabase.tasksDao()
        mAllUnfinishedTasks = mTaskDao.getNieWykonaneZadania()
        mAllFinishedTasks = mTaskDao.getWykonaneZadania()
    }

    val allUnfinishedTasks: LiveData<List<Task>>
        get() = mAllUnfinishedTasks

    val allFinishedTasks: LiveData<List<Task>>
        get() = mAllFinishedTasks

    fun delete(task: Task) {
        AppDatabase.databaseWriteExecutor.execute { mTaskDao.delete(task) }
    }

    fun update(task: Task) {
        AppDatabase.databaseWriteExecutor.execute { mTaskDao.update(task) }
    }

    fun insert(task: Task) {
        AppDatabase.databaseWriteExecutor.execute { mTaskDao.insert(task) }
    }
}
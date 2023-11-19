package com.example.eisenhower.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.eisenhower.model.Task
import com.example.eisenhower.repository.TaskRepository
import androidx.lifecycle.Transformations



class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: TaskRepository
    private val mAllUnfinishedTasks: LiveData<List<Task>>
    private val mAllFinishedTasks: LiveData<List<Task>>

    init {
        mRepository = TaskRepository(application)
        mAllUnfinishedTasks = mRepository.allUnfinishedTasks
        mAllFinishedTasks = mRepository.allFinishedTasks
    }

    val allUnfinishedTasks: LiveData<List<Task>>
        get() = mAllUnfinishedTasks

    val allFinishedTasks: LiveData<List<Task>>
        get() = mAllFinishedTasks

    fun insert(task: Task) {
        mRepository.insert(task)
    }

    fun delete(task: Task) {
        mRepository.delete(task)
    }

    fun update(task: Task) {
        mRepository.update(task)
    }
    fun getTasksByPriority(priority: Int): LiveData<List<Task>> {
        return Transformations.map(allUnfinishedTasks) { tasks ->
            tasks.filter { it.priority == priority }
        }
    }
}


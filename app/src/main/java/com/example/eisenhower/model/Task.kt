package com.example.eisenhower.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val priority: Int,
    val title: String,
    val description: String,
    val date: Date,
    val isDone: Boolean = false
)

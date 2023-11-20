package com.example.eisenhower.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "block")
data class Block(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val color: Int,
    val priority: Int
)


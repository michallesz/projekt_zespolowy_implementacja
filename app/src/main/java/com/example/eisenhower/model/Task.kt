package com.example.eisenhower.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "zadania")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val priorytet: Int,
    val tytul: String,
    val opis: String,
    val dataPowiadomienia: Date,
    val czyWykonane: Boolean = false
)

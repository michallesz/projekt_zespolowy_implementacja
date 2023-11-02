package com.example.eisenhower.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zadania")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val tytul: String,
    val opis: String,
    val priorytet: String,
    val dataPowiadomienia: Long,
    val czyWykonane: Boolean = false
)

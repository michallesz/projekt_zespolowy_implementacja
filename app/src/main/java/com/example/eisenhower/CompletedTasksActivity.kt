package com.example.eisenhower

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CompletedTasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_tasks)
        title = "Wykonane zadania"
    }
}
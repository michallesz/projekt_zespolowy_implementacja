package com.example.eisenhower

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val addButton: Button = findViewById(R.id.addTaskButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        val settingsButton: Button = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val completedTasksButton: Button = findViewById(R.id.completedTasksButton)
        completedTasksButton.setOnClickListener {
            val intent = Intent(this, CompletedTasksActivity::class.java)
            startActivity(intent)
        }
    }
}
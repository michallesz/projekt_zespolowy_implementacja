package com.example.eisenhower

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.adapter.TaskAdapter
import com.example.eisenhower.viewmodel.TaskViewModel

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_task_details)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // powrot
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }

        val title = intent.getStringExtra("blockTitle") ?: "Blok nieznany"
        val color = intent.getStringExtra("color") ?: "#FFFFFF"
        val priority = intent.getIntExtra("priority", -1)

        // tlo
        val constraintLayout: ConstraintLayout = findViewById(R.id.taskDetailsConstraintLayout)
        constraintLayout.setBackgroundColor(Color.parseColor(color))

        // tytul
        val titleTextView: TextView = findViewById(R.id.taskMatrixTitle)
        titleTextView.text = title

        // konfiguracja RecyclerView z zadaniami
        val taskDetailsRecyclerView: RecyclerView = findViewById(R.id.taskDetailsRecyclerView)
        val adapter = TaskAdapter({ task ->
            // obsluzenie klikniecia
        }, showDetails = true)
        taskDetailsRecyclerView.adapter = adapter
        taskDetailsRecyclerView.layoutManager = LinearLayoutManager(this)

        // pobranie i wyswietlenie zadan o odpowiednim priorytecie
        taskViewModel.getTasksByPriority(priority).observe(this) { tasks ->
            adapter.setTaskList(tasks)
        }
    }
}



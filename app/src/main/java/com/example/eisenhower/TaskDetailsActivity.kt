package com.example.eisenhower

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.adapter.TaskAdapter
import com.example.eisenhower.adapter.TaskDetailsAdapter
import com.example.eisenhower.viewmodel.TaskViewModel

class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val toolbar:Toolbar = findViewById(R.id.taskToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("blockTitle") ?: "Blok nieznany"
        val color = intent.getIntExtra("color", 0)
        val priority = intent.getIntExtra("priority", -1)

        supportActionBar!!.title = title

        val toolBar: Toolbar = findViewById(R.id.taskToolbar)
        val recycler: RecyclerView = findViewById(R.id.taskDetailsRecyclerView)

        toolBar.setBackgroundColor(color)
        recycler.setBackgroundColor(color)

        val taskDetailsRecyclerView: RecyclerView = findViewById(R.id.taskDetailsRecyclerView)
        val adapter = TaskDetailsAdapter(
            onEditClick = {task ->
                AddTaskActivity.startActivity(this, task)
            }, onDoneClick = {task ->
                AlertDialog.Builder(this)
                    .setTitle("Ukończenie zadania")
                    .setMessage("Czy na pewno chcesz przenieść to zadanie do sekcji Wykonane?")
                    .setPositiveButton("Tak") { dialog, which ->
                        val newTask = task.copy(isDone = true)
                        taskViewModel.update(newTask)
                    }
                    .setNegativeButton("Anuluj", null)
                    .show()
            }
        )
        taskDetailsRecyclerView.adapter = adapter
        taskDetailsRecyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel.getTasksByPriority(priority).observe(this) { tasks ->
            adapter.setTaskList(tasks)
        }
    }
}



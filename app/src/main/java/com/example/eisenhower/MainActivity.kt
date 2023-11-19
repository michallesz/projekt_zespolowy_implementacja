package com.example.eisenhower

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.AddTaskActivity
import com.example.eisenhower.adapter.TaskAdapter
import com.example.eisenhower.model.Task
import com.example.eisenhower.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel

    private lateinit var block0List: RecyclerView
    private lateinit var block0ListAdapter: TaskAdapter

    private lateinit var block1List: RecyclerView
    private lateinit var block1ListAdapter: TaskAdapter

    private lateinit var block2List: RecyclerView
    private lateinit var block2ListAdapter: TaskAdapter

    private lateinit var block3List: RecyclerView
    private lateinit var block3ListAdapter: TaskAdapter

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        val addButton: ImageButton = findViewById(R.id.addTaskButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        val settingsButton: ImageButton = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val completedTasksButton: ImageButton = findViewById(R.id.completedTasksButton)
        completedTasksButton.setOnClickListener {
            val intent = Intent(this, CompletedTasksActivity::class.java)
            startActivity(intent)
        }

        block0List = findViewById(R.id.zero)
        block0ListAdapter = TaskAdapter(mOnItemClickListener = { task ->

        }, showDetails = false)
        block0List.adapter = block0ListAdapter
        block0List.layoutManager = LinearLayoutManager(this)

        block1List = findViewById(R.id.one)
        block1ListAdapter = TaskAdapter(mOnItemClickListener = { task ->

        }, showDetails = false)
        block1List.adapter = block1ListAdapter
        block1List.layoutManager = LinearLayoutManager(this)

        block2List = findViewById(R.id.two)
        block2ListAdapter = TaskAdapter(mOnItemClickListener = { task ->

        }, showDetails = false)
        block2List.adapter = block2ListAdapter
        block2List.layoutManager = LinearLayoutManager(this)

        block3List = findViewById(R.id.three)
        block3ListAdapter = TaskAdapter(mOnItemClickListener = { task ->

        }, showDetails = false)
        block3List.adapter = block3ListAdapter
        block3List.layoutManager = LinearLayoutManager(this)

        taskViewModel.allUnfinishedTasks.observe(this) { unfinishedTasks ->
            val zero = unfinishedTasks.filter { task -> task.priorytet == 0 }
            block0ListAdapter.setTaskList(zero)

            val one = unfinishedTasks.filter { task -> task.priorytet == 1 }
            block1ListAdapter.setTaskList(one)

            val two = unfinishedTasks.filter { task -> task.priorytet == 2 }
            block2ListAdapter.setTaskList(two)

            val three = unfinishedTasks.filter { task -> task.priorytet == 3 }
            block3ListAdapter.setTaskList(three)
        }

        //blok 1
        val block0: TextView = findViewById(R.id.block0)
        block0.setOnClickListener {
            startTaskDetailsActivity("Ważne, Pilne", 0, "#FFCCCC")
        }

        //blok2
        val block1: TextView = findViewById(R.id.block1)
        block1.setOnClickListener {
            startTaskDetailsActivity("Ważne, Niepilne", 1, "#FFDDCC")
        }

        //blok3
        val block2: TextView = findViewById(R.id.block2)
        block2.setOnClickListener {
            startTaskDetailsActivity("Nieważne, Pilne", 2, "#FFEECC")
        }

        //blok4
        val block3: TextView = findViewById(R.id.block3)
        block3.setOnClickListener {
            startTaskDetailsActivity("Nieważne, Niepilne", 3, "#FFFFCC")
        }
    }
    private fun startTaskDetailsActivity(title: String, priority: Int, color: String) {
        val intent = Intent(this, TaskDetailsActivity::class.java).apply {
            putExtra("blockTitle", title)
            putExtra("priority", priority)
            putExtra("color", color)
        }
        startActivity(intent)
    }
}
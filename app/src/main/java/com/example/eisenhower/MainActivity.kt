package com.example.eisenhower

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.adapter.TaskAdapter
import com.example.eisenhower.databinding.ActivityMainBinding
import com.example.eisenhower.databinding.ActivitySettingsBinding
import com.example.eisenhower.viewmodel.BlockViewModel
import com.example.eisenhower.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var blockViewModel: BlockViewModel

    private lateinit var block0List: RecyclerView
    private lateinit var block0ListAdapter: TaskAdapter

    private lateinit var block1List: RecyclerView
    private lateinit var block1ListAdapter: TaskAdapter

    private lateinit var block2List: RecyclerView
    private lateinit var block2ListAdapter: TaskAdapter

    private lateinit var block3List: RecyclerView
    private lateinit var block3ListAdapter: TaskAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        blockViewModel = ViewModelProvider(this)[BlockViewModel::class.java]

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
        block0ListAdapter = TaskAdapter(showDetails = false)
        block0List.adapter = block0ListAdapter
        block0List.layoutManager = LinearLayoutManager(this)

        block1List = findViewById(R.id.one)
        block1ListAdapter = TaskAdapter(showDetails = false)
        block1List.adapter = block1ListAdapter
        block1List.layoutManager = LinearLayoutManager(this)

        block2List = findViewById(R.id.two)
        block2ListAdapter = TaskAdapter(showDetails = false)
        block2List.adapter = block2ListAdapter
        block2List.layoutManager = LinearLayoutManager(this)

        block3List = findViewById(R.id.three)
        block3ListAdapter = TaskAdapter(showDetails = false)
        block3List.adapter = block3ListAdapter
        block3List.layoutManager = LinearLayoutManager(this)

        taskViewModel.allUnfinishedTasks.observe(this) { unfinishedTasks ->
            val zero = unfinishedTasks.filter { task -> task.priority == 0 }
            block0ListAdapter.setTaskList(zero)

            val one = unfinishedTasks.filter { task -> task.priority == 1 }
            block1ListAdapter.setTaskList(one)

            val two = unfinishedTasks.filter { task -> task.priority == 2 }
            block2ListAdapter.setTaskList(two)

            val three = unfinishedTasks.filter { task -> task.priority == 3 }
            block3ListAdapter.setTaskList(three)
        }

        blockViewModel.allBlocks.observe(this) { blocks ->
            val block0 = blocks.find { it.priority == 0 }!!
            val block1 = blocks.find { it.priority == 1 }!!
            val block2 = blocks.find { it.priority == 2 }!!
            val block3 = blocks.find { it.priority == 3 }!!

            binding.block0.text = block0.title
            binding.area0.setBackgroundColor(block0.color)
            binding.block0.setOnClickListener {
                startTaskDetailsActivity(block0.title, block0.priority, block0.color)
            }

            binding.block1.text = block1.title
            binding.area1.setBackgroundColor(block1.color)
            binding.block1.setOnClickListener {
                startTaskDetailsActivity(block1.title, block1.priority, block1.color)
            }

            binding.block2.text = block2.title
            binding.area2.setBackgroundColor(block2.color)
            binding.block2.setOnClickListener {
                startTaskDetailsActivity(block2.title, block2.priority, block2.color)
            }

            binding.block3.text = block3.title
            binding.area3.setBackgroundColor(block3.color)
            binding.block3.setOnClickListener {
                startTaskDetailsActivity(block3.title, block3.priority, block3.color)
            }
        }
    }

    private fun startTaskDetailsActivity(title: String, priority: Int, color: Int) {
        val intent = Intent(this, TaskDetailsActivity::class.java).apply {
            putExtra("blockTitle", title)
            putExtra("priority", priority)
            putExtra("color", color)
        }
        startActivity(intent)
    }
}
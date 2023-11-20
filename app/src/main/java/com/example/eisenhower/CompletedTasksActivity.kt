package com.example.eisenhower

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.adapter.CompletedTaskAdapter
import com.example.eisenhower.adapter.TaskDetailsAdapter
import com.example.eisenhower.viewmodel.BlockViewModel
import com.example.eisenhower.viewmodel.TaskViewModel

class CompletedTasksActivity : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var blockViewModel: BlockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_tasks)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        blockViewModel = ViewModelProvider(this).get(BlockViewModel::class.java)

        val toolbar: Toolbar = findViewById(R.id.taskToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recycler: RecyclerView = findViewById(R.id.taskCompletedRecyclerView)

        val adapter = CompletedTaskAdapter(
            onUndoneClick = {task ->
                AlertDialog.Builder(this)
                    .setTitle("Przywrócenie zadania")
                    .setMessage("Czy na pewno chcesz przywrócić to zadanie?")
                    .setPositiveButton("Tak") { dialog, which ->
                        val newTask = task.copy(isDone = false)
                        taskViewModel.update(newTask)
                    }
                    .setNegativeButton("Anuluj", null)
                    .show()
            }, onDeleteClick = {task ->
                AlertDialog.Builder(this)
                    .setTitle("Usuwanie zadania")
                    .setMessage("Czy na pewno chcesz usunąć to zadanie?")
                    .setPositiveButton("Usuń") { dialog, which ->
                        taskViewModel.delete(task)
                    }
                    .setNegativeButton("Anuluj", null)
                    .show()
            }
        )
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        taskViewModel.allFinishedTasks.observe(this) { tasks ->
            adapter.setTaskList(tasks)
        }

        blockViewModel.allBlocks.observe(this) { blocks ->
            adapter.setBlockList(blocks)
        }
    }
}
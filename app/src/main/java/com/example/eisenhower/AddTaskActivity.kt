package com.example.eisenhower

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.eisenhower.databinding.ActivityAddTaskBinding
import com.example.eisenhower.model.Task
import com.example.eisenhower.viewmodel.BlockViewModel
import com.example.eisenhower.viewmodel.TaskViewModel
import java.util.Calendar

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var blockViewModel: BlockViewModel

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        blockViewModel = ViewModelProvider(this)[BlockViewModel::class.java]

        val toolbar: Toolbar = findViewById(R.id.taskToolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.notificationTimePicker.setIs24HourView(true)

        if (intent.extras != null) {
            val extras = intent.extras!!
            if (extras.containsKey(ID_KEY)) {
                id = extras.getInt(ID_KEY)
                binding.prioritySpinner.setSelection(extras.getInt(PRIORITY_KEY))
                binding.titleEditText.setText(extras.getString(TITLE_KEY))
                binding.descriptionEditText.setText(extras.getString(DESCRIPTION_KEY))
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = extras.getLong(DATE_KEY)
                binding.notificationDatePicker.updateDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                binding.notificationTimePicker.hour = calendar.get(Calendar.HOUR_OF_DAY)
                binding.notificationTimePicker.minute = calendar.get(Calendar.MINUTE)
            }
        }

        binding.addTaskButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val priority = binding.prioritySpinner.selectedItemPosition

            val calendar = Calendar.getInstance()
            calendar.set(
                binding.notificationDatePicker.year,
                binding.notificationDatePicker.month,
                binding.notificationDatePicker.dayOfMonth,
                binding.notificationTimePicker.hour,
                binding.notificationTimePicker.minute
            )

            val notificationDate = calendar.time

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val task = Task(
                    id = id ?: 0,
                    priority = priority,
                    title = title,
                    description = description,
                    date = notificationDate
                )
                taskViewModel.insert(task)
                finish()
            } else {
                Toast.makeText(this, "Wypełnij wszystkie pola.", Toast.LENGTH_SHORT).show()
            }
        }
        blockViewModel.allBlocks.observe(this) { blocks ->
            val block0 = blocks.find { it.priority == 0 }!!
            val block1 = blocks.find { it.priority == 1 }!!
            val block2 = blocks.find { it.priority == 2 }!!
            val block3 = blocks.find { it.priority == 3 }!!

            val priorities = arrayOf(block0.title, block1.title, block2.title, block3.title)
            setupPrioritySpinner(priorities)
        }
    }

    private fun setupPrioritySpinner(priorities: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter

        id?.let {
            val selectedPriority = intent.extras?.getInt(PRIORITY_KEY) ?: 0
            binding.prioritySpinner.setSelection(selectedPriority)
        }
    }

    companion object {
        private const val ID_KEY = "id"
        private const val PRIORITY_KEY = "priority"
        private const val TITLE_KEY = "title"
        private const val DESCRIPTION_KEY = "description"
        private const val DATE_KEY = "date"
        private const val IS_DONE_KEY = "isDone"

        fun startActivity(context: Context, task: Task) {
            val bundle = Bundle()
            bundle.putInt(ID_KEY, task.id)
            bundle.putInt(PRIORITY_KEY, task.priority)
            bundle.putString(TITLE_KEY, task.title)
            bundle.putString(DESCRIPTION_KEY, task.description)
            bundle.putLong(DATE_KEY, task.date.time)
            bundle.putBoolean(IS_DONE_KEY, task.isDone)

            val intent = Intent(context, AddTaskActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
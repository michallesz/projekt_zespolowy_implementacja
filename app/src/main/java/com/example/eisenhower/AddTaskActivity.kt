package com.example.eisenhower

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eisenhower.database.AppDatabase
import com.example.eisenhower.databinding.ActivityAddTaskBinding
import com.example.eisenhower.model.Task
import com.example.eisenhower.viewmodel.TaskViewModel
import java.util.Calendar
import java.util.Date

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var taskViewModel: TaskViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        setupPrioritySpinner()

        binding.addTaskButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()
            val priority = binding.prioritySpinner.selectedItemPosition

            val calendar = Calendar.getInstance()
            calendar.set(binding.notificationDatePicker.year, binding.notificationDatePicker.month, binding.notificationDatePicker.dayOfMonth, binding.notificationTimePicker.hour, binding.notificationTimePicker.minute)

            val notificationDate = calendar.time

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val task = Task(priorytet = priority, tytul = title, opis = description, dataPowiadomienia = notificationDate)
                taskViewModel.insert(task)
                finish()
            } else {
                Toast.makeText(this, "Wypełnij wszystkie pola.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupPrioritySpinner() {
        val priorities = arrayOf("Ważne, Pilne", "Ważne, Niepilne", "Nieważne, Pilne", "Nieważne, Niepilne")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
    }
}

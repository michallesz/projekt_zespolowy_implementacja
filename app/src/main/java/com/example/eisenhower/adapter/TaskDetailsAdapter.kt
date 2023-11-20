package com.example.eisenhower.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.AddTaskActivity
import com.example.eisenhower.R
import com.example.eisenhower.model.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskDetailsAdapter(
    private val onEditClick: (task: Task) -> Unit, private val onDoneClick: (task: Task) -> Unit

) : RecyclerView.Adapter<TaskDetailsAdapter.DetailsViewHolder>() {

    private var mTaskList: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item_details, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val task = mTaskList[position]
        holder.bind(task)
    }

    inner class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val descriptionTextView: TextView =
            itemView.findViewById(R.id.taskDescriptionTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.taskDateTextView)

        private val editIcon: ImageButton = itemView.findViewById(R.id.editTaskButton)
        private val doneIcon: ImageButton = itemView.findViewById(R.id.doneTaskButton)


        fun bind(task: Task) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            dateTextView.text = dateFormat.format(task.date)

            editIcon.setOnClickListener {
                onEditClick(task)
            }

            doneIcon.setOnClickListener {
                onDoneClick(task)
            }
        }
    }

    override fun getItemCount(): Int {
        return mTaskList.size
    }

    fun setTaskList(taskList: List<Task>) {
        mTaskList = taskList.toMutableList()
        notifyDataSetChanged()
    }
}


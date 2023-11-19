package com.example.eisenhower.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
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

class TaskAdapter(
    private val showDetails: Boolean // flaga decydujaca czy tytul czy detale
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mTaskList: MutableList<Task> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        // zwracany typ widoku na podstawie flagi
        return if (showDetails) VIEW_TYPE_DETAILS else VIEW_TYPE_TITLE_ONLY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TITLE_ONLY -> {
                val view = inflater.inflate(R.layout.task_item, parent, false)
                TitleViewHolder(view)
            }
            VIEW_TYPE_DETAILS -> {
                val view = inflater.inflate(R.layout.task_item_details, parent, false)
                DetailsViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task = mTaskList[position]
        when (holder) {
            is TitleViewHolder -> holder.bind(task)
            is DetailsViewHolder -> holder.bind(task)
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTextView)

        fun bind(task: Task) {
            val spannableString = SpannableString(task.title).apply {
                setSpan(BulletSpan(15, Color.BLACK, 10), 0, task.title.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }

            titleTextView.text = spannableString
        }
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val descriptionTextView: TextView =
            itemView.findViewById(R.id.taskDescriptionTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.taskDateTextView)

        private val editIcon: ImageButton = itemView.findViewById(R.id.editTaskButton)

        fun bind(task: Task) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            dateTextView.text = dateFormat.format(task.date)

            editIcon.setOnClickListener{
                AddTaskActivity.startActivity(itemView.context,task)
            }
        }
    }

    override fun getItemCount(): Int {
        return mTaskList.size
    }

    companion object {
        const val VIEW_TYPE_TITLE_ONLY = 0
        const val VIEW_TYPE_DETAILS = 1
    }

    fun setTaskList(taskList: List<Task>) {
        mTaskList = taskList.toMutableList()
        notifyDataSetChanged()
    }
}


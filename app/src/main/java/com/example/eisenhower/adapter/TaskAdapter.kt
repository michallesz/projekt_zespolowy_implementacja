package com.example.eisenhower.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.R
import com.example.eisenhower.model.Task

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TitleViewHolder>() {

    private var mTaskList: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return TitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val task = mTaskList[position]
        holder.bind(task)
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTextView)

        fun bind(task: Task) {
            val blackAlpha = Color.argb(153, 0,0,0)
            val spannableString = SpannableString(task.title).apply {
                setSpan(
                    BulletSpan(15, blackAlpha, 10),
                    0,
                    task.title.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }

            titleTextView.text = spannableString
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


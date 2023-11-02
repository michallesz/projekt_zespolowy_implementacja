package com.example.eisenhower.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.R
import com.example.eisenhower.model.Task

class TaskAdapter(private val mOnItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var mTaskList: MutableList<Task>

    fun interface OnItemClickListener {
        fun onItemClickListener(element: Task)
    }

    init {
        mTaskList = mutableListOf()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView: View = inflater.inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(rootView)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        holder.bindToTaskViewHolder(position)
        val task: Task = mTaskList[position]
        holder.itemView.tag = task
    }

    override fun getItemCount(): Int {
        return mTaskList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var taskTextView: TextView

        init {
            taskTextView = itemView.findViewById<TextView>(R.id.taskTextView)
            itemView.setOnClickListener(this)
        }

        fun bindToTaskViewHolder(position: Int) {
            taskTextView.text = "· ${mTaskList[position].tytul}"
        }

        override fun onClick(view: View) {
            val task: Task = view.tag as Task
            mOnItemClickListener.onItemClickListener(task)
        }
    }

    fun setTaskList(taskList: List<Task>) {
        mTaskList = taskList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeAt(position: Int): Task {
        val removedObject: Task = mTaskList.removeAt(position)
        notifyItemRemoved(position)
        return removedObject
    }
}

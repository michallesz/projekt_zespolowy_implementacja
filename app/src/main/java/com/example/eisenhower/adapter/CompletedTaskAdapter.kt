package com.example.eisenhower.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eisenhower.R
import com.example.eisenhower.model.Block
import com.example.eisenhower.model.Task
import java.text.SimpleDateFormat
import java.util.*

class CompletedTaskAdapter(
    private val onUndoneClick: (task: Task) -> Unit, private val onDeleteClick: (task: Task) -> Unit

) : RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>() {

    private var mTaskList: MutableList<Task> = mutableListOf()
    private var mBlockList: MutableList<Block> = mutableListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item_completed, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = mTaskList[position]
        holder.bind(task)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priorityTextView: TextView = itemView.findViewById(R.id.taskPriorityTextView)
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val descriptionTextView: TextView =
            itemView.findViewById(R.id.taskDescriptionTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.taskDateTextView)

        private val undoneIcon: ImageButton = itemView.findViewById(R.id.setUndoneTaskButton)
        private val deleteIcon: ImageButton = itemView.findViewById(R.id.deleteTaskButton)


        fun bind(task: Task) {
            priorityTextView.text = getPriorityTitle(task)
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            dateTextView.text = dateFormat.format(task.date)

            undoneIcon.setOnClickListener {
                onUndoneClick(task)
            }

            deleteIcon.setOnClickListener {
                onDeleteClick(task)
            }
        }

        private fun getPriorityTitle(task: Task): String{
            val block = mBlockList.find { block ->
                block.priority == task.priority
            }
            if(block != null){
                return block.title
            }else{
                return ""
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

    fun setBlockList(blockList: List<Block>) {
        mBlockList = blockList.toMutableList()
        notifyDataSetChanged()
    }
}


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
import java.text.SimpleDateFormat
import java.util.*


//class TaskAdapter(private val mOnItemClickListener: OnItemClickListener, private val showDetails: Boolean) :
//    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
//    private var mTaskList: MutableList<Task>
//
//    fun interface OnItemClickListener {
//        fun onItemClickListener(element: Task)
//    }
//
//    init {
//        mTaskList = mutableListOf()
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): TaskViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val rootView: View = inflater.inflate(R.layout.task_item_details, parent, false)
//        return TaskViewHolder(rootView)
//    }
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        val task = mTaskList[position]
//        holder.taskTitleTextView.text = task.tytul
//
//        if (showDetails) {
//            // Show description and date in TaskDetailsActivity
//            holder.taskDescriptionTextView.visibility = View.VISIBLE
//            holder.taskDateTextView.visibility = View.VISIBLE
//
//            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
//            holder.taskDateTextView.text = dateFormat.format(task.dataPowiadomienia)
//            holder.taskDescriptionTextView.text = task.opis
//        } else {
//            // Hide description and date in MainActivity
//            holder.taskDescriptionTextView.visibility = View.GONE
//            holder.taskDateTextView.visibility = View.GONE
//        }
//
////        holder.taskDescriptionTextView.text = task.opis
////        // Formatowanie daty i ustawienie jej w TextView
////        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
////        holder.taskDateTextView.text = dateFormat.format(task.dataPowiadomienia)
//
//        // Ustawienie listenera kliknięcia
//        holder.itemView.setOnClickListener { mOnItemClickListener.onItemClickListener(task) }
//    }
//
//
//    override fun getItemCount(): Int {
//        return mTaskList.size
//    }
//
//    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
//
//        View.OnClickListener {
//        //private var taskTextView: TextView
//        var taskTitleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
//        var taskDescriptionTextView: TextView = itemView.findViewById(R.id.taskDescriptionTextView)
//        var taskDateTextView: TextView = itemView.findViewById(R.id.taskDateTextView)
//
//
//        init {
//            //taskTextView = itemView.findViewById<TextView>(R.id.taskTextView)
//            itemView.setOnClickListener(this)
//        }
//
//        fun bindToTaskViewHolder(position: Int) {
//            //taskTextView.text = "· ${mTaskList[position].tytul}"
//        }
//
//        override fun onClick(view: View) {
//            val task: Task = view.tag as Task
//            mOnItemClickListener.onItemClickListener(task)
//        }
//    }
//
//    fun setTaskList(taskList: List<Task>) {
//        mTaskList = taskList.toMutableList()
//        notifyDataSetChanged()
//    }
//
//    fun removeAt(position: Int): Task {
//        val removedObject: Task = mTaskList.removeAt(position)
//        notifyItemRemoved(position)
//        return removedObject
//    }
//}
class TaskAdapter(
    private val mOnItemClickListener: OnItemClickListener,
    private val showDetails: Boolean // flaga decydujaca czy tytul czy detale
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mTaskList: MutableList<Task> = mutableListOf()

    fun interface OnItemClickListener {
        fun onItemClickListener(element: Task)
    }

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
//            titleTextView.text = "· ${task.tytul}"
//            // on click listener
            val spannableString = SpannableString(task.tytul).apply {
                setSpan(BulletSpan(15, Color.BLACK, 10), 0, task.tytul.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }

            titleTextView.text = spannableString
            // on click listener
        }
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.taskTitleTextView)
        private val descriptionTextView: TextView =
            itemView.findViewById(R.id.taskDescriptionTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.taskDateTextView)

        fun bind(task: Task) {
            titleTextView.text = task.tytul
            descriptionTextView.text = task.opis
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            dateTextView.text = dateFormat.format(task.dataPowiadomienia)
            // on click listener
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

    fun removeAt(position: Int): Task {
        val removedObject: Task = mTaskList.removeAt(position)
        notifyItemRemoved(position)
        return removedObject
    }
}


package ru.mishlak.lab3_1_todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
	private val onNotebookClick: (Task) -> Unit,
	private val onLongClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_task, parent, false)
		return TaskViewHolder(view)
	}

	override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
		val task = getItem(position)
		holder.bind(task)
	}

	inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val descriptionTextView: TextView = itemView.findViewById(R.id.tvDescription)

		fun bind(task: Task) {
			descriptionTextView.text = task.description

			itemView.setOnLongClickListener {
				onLongClick(task)
				true
			}
		}
	}
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
	override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
		return oldItem == newItem
	}
}
package ru.mishlak.lab3_1_todo

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.mishlak.lab3_1_todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var taskAdapter: TaskAdapter
	private lateinit var databaseHelper: TaskDatabaseHelper

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		databaseHelper = TaskDatabaseHelper(this)

		setupRecyclerView()
		setupClickListeners()
		observeData()
	}

	private fun setupRecyclerView() {
		taskAdapter = TaskAdapter(
			onNotebookClick = { task ->
				showNotebookDialog(task)
			},
			onLongClick = { task ->
				deleteTask(task)
			}
		)

		binding.rvTasks.apply {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = taskAdapter
		}
	}

	private fun setupClickListeners() {
		// Теперь используем btnAdd вместо fabAdd
		binding.btnAdd.setOnClickListener {
			addTask()
		}

		binding.etTaskInput.setOnEditorActionListener { _, actionId, _ ->
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				addTask()
				true
			} else {
				false
			}
		}
	}

	// остальные методы без изменений...
	private fun observeData() {
		lifecycleScope.launch {
			databaseHelper.tasksFlow.collect { tasks ->
				taskAdapter.submitList(tasks)
			}
		}

		lifecycleScope.launch {
			databaseHelper.taskCountFlow.collect { count ->
				binding.tvTotalTasks.text = "Total tasks: $count"
			}
		}
	}

	private fun addTask() {
		val taskDescription = binding.etTaskInput.text.toString().trim()
		if (taskDescription.isNotEmpty()) {
			lifecycleScope.launch {
				val task = Task(description = taskDescription)
				databaseHelper.addTask(task)
				binding.etTaskInput.text?.clear()
			}
		} else {
			Snackbar.make(binding.root, "Please enter a task", Snackbar.LENGTH_SHORT).show()
		}
	}

	private fun deleteTask(task: Task) {
		MaterialAlertDialogBuilder(this)
			.setTitle("Delete Task")
			.setMessage("Are you sure you want to delete this task?")
			.setPositiveButton("Delete") { _, _ ->
				lifecycleScope.launch {
					databaseHelper.deleteTask(task.id)
				}
			}
			.setNegativeButton("Cancel", null)
			.show()
	}

	private fun showNotebookDialog(task: Task) {
		Snackbar.make(binding.root, "Notebook for: ${task.description}", Snackbar.LENGTH_SHORT).show()
	}
}
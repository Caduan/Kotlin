package ru.mishlak.lab3_1_todo

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskRepository {

	private val _tasks = MutableStateFlow<List<Task>>(emptyList())
	val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

	private val _taskCount = MutableStateFlow(0)
	val taskCount: StateFlow<Int> = _taskCount.asStateFlow()

	private var nextId = 1L

	init {
		// Добавим несколько тестовых задач для демонстрации
		addTask(Task(description = "Пример задачи 1"))
		addTask(Task(description = "Пример задачи 2"))
	}

	fun getAllTasks(): List<Task> {
		return _tasks.value
	}

	fun addTask(task: Task) {
		val newTask = task.copy(id = nextId++)
		val currentTasks = _tasks.value.toMutableList()
		currentTasks.add(0, newTask) // Добавляем в начало списка
		_tasks.value = currentTasks
		_taskCount.value = currentTasks.size
	}

	fun deleteTask(task: Task) {
		val currentTasks = _tasks.value.toMutableList()
		currentTasks.removeAll { it.id == task.id }
		_tasks.value = currentTasks
		_taskCount.value = currentTasks.size
	}

	fun getTasksFlow(): StateFlow<List<Task>> {
		return tasks
	}

	fun getTaskCountFlow(): StateFlow<Int> {
		return taskCount
	}
}
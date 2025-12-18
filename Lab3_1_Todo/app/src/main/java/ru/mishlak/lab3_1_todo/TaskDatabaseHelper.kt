package ru.mishlak.lab3_1_todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	companion object {
		private const val DATABASE_NAME = "tasks.db"
		private const val DATABASE_VERSION = 1
		private const val TABLE_TASKS = "tasks"
		private const val COLUMN_ID = "id"
		private const val COLUMN_DESCRIPTION = "description"
		private const val COLUMN_CREATED_AT = "created_at"
	}

	private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList())
	val tasksFlow: StateFlow<List<Task>> = _tasksFlow

	private val _taskCountFlow = MutableStateFlow(0)
	val taskCountFlow: StateFlow<Int> = _taskCountFlow

	init {
		updateFlows()
	}

	override fun onCreate(db: SQLiteDatabase) {
		val createTable = """
            CREATE TABLE $TABLE_TASKS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DESCRIPTION TEXT NOT NULL,
                $COLUMN_CREATED_AT INTEGER NOT NULL
            )
        """.trimIndent()
		db.execSQL(createTable)
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
		onCreate(db)
	}

	fun addTask(task: Task): Long {
		val db = writableDatabase
		val values = ContentValues().apply {
			put(COLUMN_DESCRIPTION, task.description)
			put(COLUMN_CREATED_AT, task.createdAt)
		}
		val newRowId = db.insert(TABLE_TASKS, null, values)
		db.close()
		updateFlows()
		return newRowId
	}

	fun getAllTasks(): List<Task> {
		val tasks = mutableListOf<Task>()
		val db = readableDatabase
		val cursor: Cursor = db.query(
			TABLE_TASKS,
			arrayOf(COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_CREATED_AT),
			null, null, null, null, "$COLUMN_CREATED_AT DESC"
		)

		with(cursor) {
			while (moveToNext()) {
				val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
				val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
				val createdAt = getLong(getColumnIndexOrThrow(COLUMN_CREATED_AT))
				tasks.add(Task(id, description, createdAt))
			}
		}
		cursor.close()
		db.close()
		return tasks
	}

	fun deleteTask(taskId: Long): Boolean {
		val db = writableDatabase
		val deletedRows = db.delete(TABLE_TASKS, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
		db.close()
		updateFlows()
		return deletedRows > 0
	}

	fun getTaskCount(): Int {
		val db = readableDatabase
		val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_TASKS", null)
		var count = 0
		if (cursor.moveToFirst()) {
			count = cursor.getInt(0)
		}
		cursor.close()
		db.close()
		return count
	}

	private fun updateFlows() {
		val tasks = getAllTasks()
		val count = getTaskCount()
		_tasksFlow.value = tasks
		_taskCountFlow.value = count
	}
}
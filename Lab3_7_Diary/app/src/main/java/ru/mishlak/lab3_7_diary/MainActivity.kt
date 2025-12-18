package ru.mishlak.lab3_7_diary

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		val titleInput = findViewById<EditText>(R.id.event_title_user_input)
		val timeInput = findViewById<EditText>(R.id.event_time_user_input)
		val notesInput = findViewById<EditText>(R.id.event_notes_user_input)
		val calendar = findViewById<CalendarView>(R.id.calendar_view)
		val saveButton = findViewById<Button>(R.id.save_button)

		saveButton.setOnClickListener {
			if (titleInput.text.toString().trim().isEmpty()) {
				Snackbar.make(it, "Введите название события!", Snackbar.LENGTH_LONG).show()
				return@setOnClickListener
			}

			val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
			val selectedDate = sdf.format(Date(calendar.date))

			AlertDialog.Builder(this)
				.setTitle("Записано!")
				.setMessage(
					"Событие: ${titleInput.text}\n" +
							"Дата: $selectedDate\n" +
							"Время: ${timeInput.text}\n" +
							"Заметки: ${notesInput.text}"
				)
				.setPositiveButton("OK", null)
				.show()

			titleInput.text.clear()
			timeInput.text.clear()
			notesInput.text.clear()
		}
	}
}
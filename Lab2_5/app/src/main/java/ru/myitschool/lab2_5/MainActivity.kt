package ru.myitschool.lab2_5

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
	private var totalClicks = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val scoreText = findViewById<TextView>(R.id.score_text)

		// Явно указываем типы для каждой FAB
		val fabTopLeft = findViewById<FloatingActionButton>(R.id.fab_top_left)
		val fabTopRight = findViewById<FloatingActionButton>(R.id.fab_top_right)
		val fabBottomLeft = findViewById<FloatingActionButton>(R.id.fab_bottom_left)
		val fabBottomRight = findViewById<FloatingActionButton>(R.id.fab_bottom_right)

		val fabs = listOf(fabTopLeft, fabTopRight, fabBottomLeft, fabBottomRight)

		// Обработка нажатий на FAB
		fabs.forEach { fab ->
			fab.setOnClickListener {
				totalClicks++
				scoreText.text = totalClicks.toString()
			}
		}

		// Сброс счетчика при нажатии на TextView
		scoreText.setOnClickListener {
			totalClicks = 0
			scoreText.text = "0"
			Snackbar.make(it, "100%", Snackbar.LENGTH_SHORT).show()
		}
	}
}
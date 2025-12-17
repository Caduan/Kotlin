package ru.myitschool.lab2_8

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val spinner: Spinner = findViewById(R.id.spinner)
		ArrayAdapter.createFromResource(
			this,
			R.array.spinner_options,
			android.R.layout.simple_spinner_item
		).also { adapter ->
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
			spinner.adapter = adapter
		}

		val calculate: Button = findViewById(R.id.calculate)
		calculate.setOnClickListener {
			calculateResult()
		}

		val solution: TextView = findViewById(R.id.solution)
		solution.setOnClickListener {
			copyToClipboard(solution.text.toString())
		}
	}
	private fun calculateResult() {
		val sideA = findViewById<EditText>(R.id.side_a).text.toString().toDoubleOrNull() ?: 0.0
		val sideB = findViewById<EditText>(R.id.side_b).text.toString().toDoubleOrNull() ?: 0.0
		val sideC = findViewById<EditText>(R.id.side_c).text.toString().toDoubleOrNull() ?: 0.0

		if (sideA > 1e12 || sideB > 1e12 || sideC > 1e12) {
			Toast.makeText(this, "Значения не должны превышать 10^12", Toast.LENGTH_SHORT).show()
			return
		}
		val spinner: Spinner = findViewById(R.id.spinner)
		val result = when (spinner.selectedItemPosition) {
			0 -> 4 * (sideA + sideB + sideC) // Сумма длин сторон
			1 -> Math.sqrt(sideA * sideA + sideB * sideB + sideC * sideC) // Диагональ
			2 -> 2 * (sideA * sideB + sideA * sideC + sideB * sideC) // Площадь поверхности
			3 -> sideA * sideB * sideC // Объем
			else -> 0.0
		}
		val solutionText = findViewById<TextView>(R.id.solution)
		solutionText.text = formatResult(result)
	}
	private fun formatResult(value: Double): String {
		val formatted = String.format(Locale.US, "%.5f", value)
		return if (formatted.contains('.')) {
			var trimmed = formatted.trimEnd('0')
			if (trimmed.endsWith('.')) {
				trimmed = trimmed.dropLast(1)
			}
			trimmed.replace('.', ',')
		} else {
			formatted
		}
	}
	private fun copyToClipboard(text: String) {
		val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
		val clip = ClipData.newPlainText("Result", text)
		clipboard.setPrimaryClip(clip)
		Toast.makeText(this, "Результат скопирован", Toast.LENGTH_SHORT).show()
	}
}
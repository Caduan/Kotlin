package ru.myitschool.lab2_6

// MainActivity.kt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

		val meanInput = findViewById<EditText>(R.id.mean_val)
		val varianceInput = findViewById<EditText>(R.id.variance_value)
		val generateButton = findViewById<Button>(R.id.get_random_num)
		val resultText = findViewById<TextView>(R.id.random_number_result)

		// Восстановление результата после поворота
		viewModel.randomResult.observe(this) { result ->
			resultText.text = result
		}

		generateButton.setOnClickListener {
			val mean = meanInput.text.toString().toDoubleOrNull() ?: 0.0
			val variance = varianceInput.text.toString().toDoubleOrNull() ?: 0.0

			viewModel.generateLognormal(mean, variance)
		}
	}
}
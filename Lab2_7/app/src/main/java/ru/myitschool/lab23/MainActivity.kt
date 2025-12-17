package ru.myitschool.lab2_7

import android.os.Bundle
import android.widget.Checkable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val showActionButton = findViewById<MaterialButton>(R.id.show_action)
		val summaryText = findViewById<MaterialTextView>(R.id.summary_text)

		showActionButton.setOnClickListener {
			val result = StringBuilder()

			// Проверяем элементы в строгом порядке
			val elements = listOf(
				"com.google.android.material.chip.Chip" to R.id.chip_material,
				"com.google.android.material.checkbox.MaterialCheckBox" to R.id.check_box_material,
				"com.google.android.material.switchmaterial.SwitchMaterial" to R.id.switch_material,
				"androidx.appcompat.widget.AppCompatToggleButton" to R.id.toggle_button
			)

			for ((canonicalName, id) in elements) {
				val view = findViewById<android.view.View>(id)
				if (view != null && view is Checkable) {
					val state = view.isChecked
					result.append("$canonicalName:$state\n")
				}
			}

			summaryText.text = result.toString().trim()
		}
	}
}
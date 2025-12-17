package ru.myitschool.lab2_4
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.myitschool.lab2_4.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private var lower = 0
	private var upper = 0
	private val editTexts = arrayOfNulls<EditText>(25)
	private var isUpdating = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		lower = intent.getIntExtra("lower", 0)
		upper = intent.getIntExtra("upper", 24)

		setupUI()
	}

	private fun setupUI() {
		val captions = resources.getStringArray(R.array.text_view_captions)
		val outerLayout = binding.container.outerLayout

		// Устанавливаем вес для равномерного распределения
		outerLayout.weightSum = (upper - lower + 1).toFloat()

		for (i in lower..upper) {
			val horizontalLayout = LinearLayout(this).apply {
				orientation = LinearLayout.HORIZONTAL
				layoutParams = LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					0,
					1.0f
				).apply {
					setMargins(8, 4, 8, 4)
				}
				gravity = Gravity.CENTER_VERTICAL
			}

			val textView = TextView(this).apply {
				text = captions[i]
				layoutParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					1f
				).apply {
					gravity = Gravity.START
				}
				setPadding(16, 8, 16, 8)
				setTextColor(Color.GREEN) // Зеленый цвет текста
				textSize = 14f
				setOnClickListener {
					val text = editTexts[i]?.text?.toString() ?: ""
					copyToClipboard(text)
				}
			}

			val editText = EditText(this).apply {
				tag = MetricsData.getTag(i)
				layoutParams = LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.WRAP_CONTENT,
					1f
				)
				setPadding(16, 8, 16, 8)
				textSize = 14f
				gravity = Gravity.END
				addTextChangedListener(createTextWatcher(i))
			}

			horizontalLayout.addView(textView)
			horizontalLayout.addView(editText)
			outerLayout.addView(horizontalLayout)

			editTexts[i] = editText
		}
	}

	private fun createTextWatcher(index: Int): TextWatcher {
		return object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

			override fun afterTextChanged(s: Editable?) {
				if (isUpdating) return

				val inputText = s?.toString()
				if (inputText.isNullOrEmpty()) {
					clearAllFieldsExcept(index)
					return
				}

				try {
					val value = inputText.toDouble()
					if (!MetricsData.isValidInput(value, index)) {
						return
					}
					updateAllFields(value, index)
				} catch (e: NumberFormatException) {
					// Игнорируем некорректный ввод
				}
			}
		}
	}

	private fun updateAllFields(baseValue: Double, sourceIndex: Int) {
		isUpdating = true

		for (i in lower..upper) {
			if (i == sourceIndex) continue

			val convertedValue = MetricsData.convertValue(baseValue, sourceIndex, i)
			val formattedValue = formatValue(convertedValue)
			editTexts[i]?.setText(formattedValue)
		}

		isUpdating = false
	}

	private fun clearAllFieldsExcept(exceptIndex: Int) {
		isUpdating = true

		for (i in lower..upper) {
			if (i == exceptIndex) continue
			editTexts[i]?.setText("")
		}

		isUpdating = false
	}

	private fun formatValue(value: Double): String {
		return when {
			value == 0.0 -> "0.0"
			Math.abs(value) < 1e-5 || Math.abs(value) > 1e10 -> {
				String.format("%.5e", value)
			}
			else -> {
				var formatted = String.format("%.5f", value)
				while (formatted.contains('.') && (formatted.endsWith('0') || formatted.endsWith('.'))) {
					formatted = formatted.dropLast(1)
				}
				if (formatted.isEmpty()) "0.0" else formatted
			}
		}
	}

	private fun copyToClipboard(text: String) {
		val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
		val clip = ClipData.newPlainText("converted_value", text)
		clipboard.setPrimaryClip(clip)
	}
}
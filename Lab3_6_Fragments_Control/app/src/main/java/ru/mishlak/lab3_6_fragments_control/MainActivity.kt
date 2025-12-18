package ru.mishlak.lab3_6_fragments_control

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
	private lateinit var handler: Handler
	private lateinit var runnable: Runnable
	private var isRunning = false
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		handler = Handler(Looper.getMainLooper())
		setupFragmentSwitch()
		setupClickListeners()
	}

	private fun setupFragmentSwitch() {
		runnable = object : Runnable {
			override fun run() {
				if (!isRunning) return

				val currentFragment = supportFragmentManager.findFragmentById(R.id.output_fragment)
				val newFragment = if (currentFragment is FirstFragment)
					ProceedingFragment()
				else
					FirstFragment()

				supportFragmentManager.beginTransaction()
					.replace(R.id.output_fragment, newFragment)
					.commit()

				handler.postDelayed(this, 3000)
			}
		}
	}

	private fun setupClickListeners() {
		findViewById<Button>(R.id.start_btn).setOnClickListener {
			if (!isRunning) {
				isRunning = true
				handler.post(runnable)
			}
		}

		findViewById<Button>(R.id.stop_btn).setOnClickListener {
			isRunning = false
			handler.removeCallbacks(runnable)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		handler.removeCallbacks(runnable)
	}
}
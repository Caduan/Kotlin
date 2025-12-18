package ru.mishlak.lab3_5_fragments

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: FragmentViewModel
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		viewModel = ViewModelProvider(this)[FragmentViewModel::class.java]
		if (savedInstanceState == null) {
			supportFragmentManager.beginTransaction()
				.replace(R.id.fragment1, RedFragment())
				.replace(R.id.fragment2, BlueFragment())
				.commit()
		}
		findViewById<android.widget.Button>(R.id.caption).setOnClickListener {
			viewModel.swapFragments()
			updateFragments()
		}
	}

	private fun updateFragments() {
		val fragment1 = if (viewModel.isRedFirst) RedFragment() else BlueFragment()
		val fragment2 = if (viewModel.isRedFirst) BlueFragment() else RedFragment()

		supportFragmentManager.beginTransaction()
			.replace(R.id.fragment1, fragment1)
			.replace(R.id.fragment2, fragment2)
			.commit()
	}
}
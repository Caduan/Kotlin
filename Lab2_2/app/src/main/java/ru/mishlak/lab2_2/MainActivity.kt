package ru.mishlak.lab2_2

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
	private var rotationCount = 0;
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(findViewById(R.id.toolbar));
		supportActionBar?.title = "Контроллёр состояния";
		showToast(R.string.ncreate);
		if (savedInstanceState != null) {
			rotationCount = savedInstanceState.getInt("ROTATION_COUNT", 0);
		}
	}
	override fun onStart() {
		super.onStart();
		showToast(R.string.nstart);
	}
	override fun onResume() {
		super.onResume();
		showToast(R.string.nresume);
	}
	override fun onPause() {
		super.onPause();
		showToast(R.string.npause)
	}
	override fun onStop() {
		super.onStop();
		showToast(R.string.nstop)
	}
	override fun onRestart() {
		super.onRestart();
		showToast(R.string.nrestart)
	}

	override fun onDestroy() {
		super.onDestroy();
		if (isChangingConfigurations && rotationCount % 2 == 1) {
			showToast(R.string.ndestroy);
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState);
		if (!isChangingConfigurations) {
			rotationCount++;
			outState.putInt("ROTATION_COUNT", rotationCount);
		}
	}
	private fun showToast(message: Int) {
		Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show();
	}

}
package ru.myitschool.lab2_6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random
import kotlin.math.*

class MainViewModel : ViewModel() {
	private val _randomResult = MutableLiveData<String>()
	val randomResult: LiveData<String> get() = _randomResult

	fun generateLognormal(mean: Double, variance: Double) {
		val u1 = Random.nextDouble()
		val u2 = Random.nextDouble()
		val z0 = sqrt(-2.0 * ln(u1)) * cos(2.0 * PI * u2)

		val normalValue = mean + sqrt(variance) * z0

		val logNormalValue = exp(normalValue)
		_randomResult.value = "%.4f".format(logNormalValue)
	}
}
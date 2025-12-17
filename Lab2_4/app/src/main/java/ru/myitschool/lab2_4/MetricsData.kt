package ru.myitschool.lab2_4

class MetricsData {
	companion object {
		// Коэффициенты конвертации в метры
		private val conversionFactors = doubleArrayOf(
			0.0254,    // Дюйм -> Метр
			0.9144,    // Ярд -> Метр
			0.3048,    // Фут -> Метр
			1609.344,  // Миля -> Метр
			1e24,      // Иоттаметр -> Метр
			1e21,      // Зеттаметр -> Метр
			1e18,      // Эксаметр -> Метр
			1e15,      // Петаметр -> Метр
			1e12,      // Тераметр -> Метр
			1e9,       // Гигаметр -> Метр
			1e6,       // Мегаметр -> Метр
			1000.0,    // Километр -> Метр
			100.0,     // Гектометр -> Метр
			10.0,      // Декаметр -> Метр
			1.0,       // Метр -> Метр
			0.1,       // Дециметр -> Метр
			0.01,      // Сантиметр -> Метр
			0.001,     // Миллиметр -> Метр
			1e-6,      // Микрометр -> Метр
			1e-9,      // Нанометр -> Метр
			1e-12,     // Пикометр -> Метр
			1e-15,     // Фемтометр -> Метр
			1e-18,     // Аттометр -> Метр
			1e-21,     // Зептометр -> Метр
			1e-24      // Иоктометр -> Метр
		)

		private val maxValues = doubleArrayOf(
			1e15, 1e14, 1e14, 1e10, 1e3, 1e4, 1e5, 1e6, 1e7, 1e8,
			1e9, 1e10, 1e11, 1e12, 1e13, 1e14, 1e15, 1e16, 1e17, 1e18,
			1e19, 1e20, 1e21, 1e22, 1e23
		)

		private val tags = arrayOf(
			"et_inch", "et_yard", "et_foot", "et_mile",
			"et_yottametre", "et_zettametre", "et_exametre", "et_petametre", "et_terametre", "et_gigametre",
			"et_megametre", "et_kilometre", "et_hectometre", "et_decametre", "et_metre",
			"et_decimetre", "et_centimetre", "et_millimetre", "et_micrometre", "et_nanometre",
			"et_picometre", "et_femtometre", "et_attometre", "et_zeptometre", "et_yoctometre"
		)

		fun getConversionFactor(index: Int): Double {
			return conversionFactors.getOrNull(index) ?: 1.0
		}

		fun getMaxValue(index: Int): Double {
			return maxValues.getOrNull(index) ?: 1e20
		}

		fun getTag(index: Int): String {
			return tags.getOrNull(index) ?: "et_unknown"
		}

		fun convertValue(value: Double, fromIndex: Int, toIndex: Int): Double {
			if (fromIndex == toIndex) return value
			val fromFactor = getConversionFactor(fromIndex)
			val toFactor = getConversionFactor(toIndex)
			val meters = value * fromFactor
			return meters / toFactor
		}

		fun isValidInput(value: Double, index: Int): Boolean {
			return value <= getMaxValue(index)
		}
	}
}
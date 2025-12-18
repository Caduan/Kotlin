package ru.mishlak.lab3_3_3_4_gena_and_phones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.mishlak.lab3_3_3_4_gena_and_phones.ui.theme.ErlangGeneratorTheme
import kotlin.math.ln
import kotlin.random.Random

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ErlangGeneratorTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					ErlangGeneratorApp()
				}
			}
		}
	}
}

@Composable
fun ErlangGeneratorApp() {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = "input") {
		composable("input") {
			InputScreen(navController)
		}
		composable("results/{n}/{k}/{lambda}") { backStackEntry ->
			val n = backStackEntry.arguments?.getString("n")?.toIntOrNull() ?: 10
			val k = backStackEntry.arguments?.getString("k")?.toIntOrNull() ?: 1
			val lambda = backStackEntry.arguments?.getString("lambda")?.toDoubleOrNull() ?: 1.0
			ResultsScreen(navController, n, k, lambda)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavHostController) {
	var n by remember { mutableStateOf("10") }
	var k by remember { mutableStateOf("1") }
	var lambda by remember { mutableStateOf("1.0") }

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = "Генератор чисел Эрланга",
			style = MaterialTheme.typography.headlineMedium,
			modifier = Modifier.padding(bottom = 32.dp)
		)

		OutlinedTextField(
			value = n,
			onValueChange = { n = it },
			label = { Text("Размер выборки (n)") },
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 16.dp),
			keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
				keyboardType = KeyboardType.Number
			)
		)

		OutlinedTextField(
			value = k,
			onValueChange = { k = it },
			label = { Text("Параметр формы (k)") },
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 16.dp),
			keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
				keyboardType = KeyboardType.Number
			)
		)

		OutlinedTextField(
			value = lambda,
			onValueChange = { lambda = it },
			label = { Text("Параметр скорости (λ)") },
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 32.dp),
			keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
				keyboardType = KeyboardType.Decimal
			)
		)

		Button(
			onClick = {
				// Переходим на экран результатов с параметрами в URL
				navController.navigate("results/$n/$k/$lambda")
			},
			modifier = Modifier.fillMaxWidth(),
			enabled = n.isNotBlank() && k.isNotBlank() && lambda.isNotBlank()
		) {
			Text("Сгенерировать числа")
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
	navController: NavHostController,
	n: Int,
	k: Int,
	lambda: Double
) {
	val numbers = remember(n, k, lambda) {
		generateErlangNumbers(n, k, lambda)
	}

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Результаты генерации") },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {

					}
				}
			)
		}
	) { paddingValues ->
		if (numbers.isEmpty()) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.padding(16.dp),
				contentAlignment = Alignment.Center
			) {
				Text("Некорректные параметры")
			}
		} else {
			LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.padding(paddingValues)
			) {
				items(numbers) { number ->
					Card(
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
					) {
						Text(
							text = String.format("%.6f", number),
							modifier = Modifier.padding(16.dp),
							style = MaterialTheme.typography.bodyLarge
						)
					}
				}
			}
		}
	}
}

fun generateErlangNumbers(n: Int, k: Int, lambda: Double): List<Double> {
	if (n <= 0 || k <= 0 || lambda <= 0.0) return emptyList()

	return List(n) {
		var sum = 0.0
		for (i in 1..k) {
			val gamma = Random.nextDouble(0.0001, 1.0) // избегаем 0 для логарифма
			sum += -ln(gamma)
		}
		sum / lambda
	}
}
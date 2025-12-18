package ru.mishlak.lab3_9_expense_tracker

import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
	private lateinit var viewModel: MainViewModel
	private lateinit var adapter: ExpenseAdapter
	private lateinit var balanceText: TextView
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		setSupportActionBar(findViewById(R.id.toolbar))

		viewModel = ViewModelProvider(this)[MainViewModel::class.java]
		balanceText = findViewById(R.id.ef_current_balance_text)
		setupRecyclerView()
		setupObservers()

		findViewById<FloatingActionButton>(R.id.add_fab).setOnClickListener {
			showAddDialog()
		}
	}

	private fun setupRecyclerView() {
		adapter = ExpenseAdapter(mutableListOf()) { position ->
			registerForContextMenu(findViewById(R.id.ef_expenses_rv))
			openContextMenu(findViewById(R.id.ef_expenses_rv))
		}
		findViewById<RecyclerView>(R.id.ef_expenses_rv).adapter = adapter
	}

	private fun setupObservers() {
		viewModel.expenses.observe(this) { expenses ->
			adapter.updateData(expenses)
			updateBalance()
		}
	}

	private fun updateBalance() {
		balanceText.text = "%.1f".format(viewModel.getBalance())
	}

	private fun showAddDialog() {
		val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_expanse, null)
		val dialog = AlertDialog.Builder(this)
			.setView(dialogView)
			.create()

		dialogView.findViewById<Button>(R.id.add_button).setOnClickListener {
			val type = dialogView.findViewById<Spinner>(R.id.type_spinner).selectedItem.toString()
			val amountText = dialogView.findViewById<EditText>(R.id.expense_amount_edit_text).text.toString()

			if (amountText.isNotEmpty()) {
				val amount = amountText.toInt()
				val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
				viewModel.addExpense(Expense(type, amount, date))
				dialog.dismiss()
			}
		}
		dialog.show()
	}


	override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
		super.onCreateContextMenu(menu, v, menuInfo)
		menu.add("Delete")
		menu.add("Duplicate")
	}

	override fun onContextItemSelected(item: MenuItem): Boolean {
		val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
		val position = info.position

		when (item.title) {
			"Delete" -> viewModel.removeExpense(position)
			"Duplicate" -> viewModel.duplicateExpense(position)
		}
		return true
	}
}
package ru.mishlak.lab3_9_expense_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
	private var expenses: MutableList<Expense>,
	private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val typeText: TextView = itemView.findViewById(R.id.expense_type_text)
		private val dateText: TextView = itemView.findViewById(R.id.expense_date_text)
		private val amountText: TextView = itemView.findViewById(R.id.expense_amount_text)

		fun bind(expense: Expense) {
			typeText.text = expense.type
			dateText.text = expense.date
			amountText.text = expense.amount.toString()
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_expanse, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(expenses[position])
		holder.itemView.setOnLongClickListener {
			onItemLongClick(position)
			true
		}
	}

	override fun getItemCount(): Int = expenses.size

	fun updateData(newExpenses: List<Expense>) {
		expenses.clear()
		expenses.addAll(newExpenses)
		notifyDataSetChanged()
	}
}
package ru.mishlak.lab3_9_expense_tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
	private val _expenses = MutableLiveData<MutableList<Expense>>()
	val expenses: LiveData<MutableList<Expense>> = _expenses

	init {
		_expenses.value = mutableListOf()
	}

	fun addExpense(expense: Expense) {
		_expenses.value = _expenses.value?.apply { add(expense) }?.toMutableList()
	}

	fun removeExpense(position: Int) {
		_expenses.value = _expenses.value?.apply { removeAt(position) }?.toMutableList()
	}

	fun duplicateExpense(position: Int) {
		_expenses.value?.get(position)?.let { expense ->
			addExpense(expense.copy())
		}
	}

	fun getBalance(): Double {
		return _expenses.value?.sumByDouble { expense ->
			when (expense.type) {
				"Income" -> expense.amount.toDouble()
				else -> -expense.amount.toDouble()
			}
		} ?: 0.0
	}
}
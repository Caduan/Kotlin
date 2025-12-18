package ru.mishlak.lab3_1_todo

data class Task(
	val id: Long = 0,
	val description: String,
	val createdAt: Long = System.currentTimeMillis()
)
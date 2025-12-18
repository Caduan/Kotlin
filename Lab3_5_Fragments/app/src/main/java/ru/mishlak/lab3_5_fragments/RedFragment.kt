package ru.mishlak.lab3_5_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class RedFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return android.widget.TextView(requireContext()).apply {
			text = "Красный фрагмент"
			setBackgroundColor(android.graphics.Color.RED)
			gravity = android.view.Gravity.CENTER
			setTextColor(android.graphics.Color.WHITE)
			textSize = 20f
		}
	}
}
package ru.mishlak.lab3_6_fragments_control

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProceedingFragment : Fragment(R.layout.fragment_proceeding) {
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.findViewById<TextView>(R.id.text_view).text = "Proceeding Fragment"
	}
}
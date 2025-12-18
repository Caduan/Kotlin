package ru.samsung.itschool.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

class BlankFragment4 : Fragment() {
	private var param1: String? = null
	private var param2: String? = null
	private var returnData: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment4, container, false)

		// ПРАВИЛЬНОЕ использование findNavController() - БЕЗ параметров
		val liveData = findNavController().currentBackStackEntry
			?.savedStateHandle
			?.getLiveData<String>("result_from_activity")

		if (liveData != null) {
			liveData.observe(viewLifecycleOwner, Observer { s ->
				returnData = s
				if (returnData != null) {
					Toast.makeText(context, returnData, Toast.LENGTH_LONG).show()
				}
			})
		}

		val et: EditText = view.findViewById(R.id.et)
		val btn6: Button = view.findViewById(R.id.button6)
		btn6.setOnClickListener {
			val bundle = Bundle()
			bundle.putString("arg1", et.text.toString())
			// Используем findNavController() без view
			findNavController().navigate(R.id.action_blankFragment4_to_main2Activity, bundle)
		}

		val btn6_up: Button = view.findViewById(R.id.button6_up)
		btn6_up.setOnClickListener {
			// Используем findNavController() без view
			findNavController().navigateUp()
		}

		val btn7: Button = view.findViewById(R.id.button7)
		btn7.setOnClickListener {
			// Используем findNavController() без view
			findNavController().navigate(R.id.action_blankFragment4_to_blankFragment32)
		}

		return view
	}

	companion object {
		private const val ARG_PARAM1 = "param1"
		private const val ARG_PARAM2 = "param2"

		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			BlankFragment4().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}
package ru.mishlak.lab3_5_fragments

import androidx.lifecycle.ViewModel

class FragmentViewModel : ViewModel() {
	var isRedFirst = true;
	fun swapFragments() {
		isRedFirst = !isRedFirst;
	}

}
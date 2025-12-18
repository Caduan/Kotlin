package ru.samsung.itschool.a311_practical_work.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.samsung.itschool.a311_practical_work.R

private val TAB_TITLES = arrayOf(
	R.string.tab_text_1,
	R.string.tab_text_2,
	R.string.tab_text_3,
	R.string.tab_text_4,
	R.string.tab_text_5
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

	override fun getItem(position: Int): Fragment {
		return PlaceholderFragment.newInstance(position + 1)
	}

	override fun getPageTitle(position: Int): CharSequence {
		return context.resources.getString(TAB_TITLES[position])
	}

	override fun getCount(): Int {
		// Show 2 total pages.
		return TAB_TITLES.size
	}
}
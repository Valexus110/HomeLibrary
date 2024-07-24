package com.valexus.homelibrary.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.valexus.homelibrary.ui.main.AllBookFragment
import com.valexus.homelibrary.ui.main.HaveReadFragment
import com.valexus.homelibrary.ui.main.WantToReadFragment

class FragmentPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllBookFragment()
            1 -> WantToReadFragment()
            else -> HaveReadFragment()
        }
    }
}
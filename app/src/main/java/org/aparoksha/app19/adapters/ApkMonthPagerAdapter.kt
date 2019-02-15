package org.aparoksha.app19.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.aparoksha.app19.fragments.CompetitionFragment
import org.aparoksha.app19.fragments.WorkshopFragment

class ApkMonthPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> CompetitionFragment()
            else -> WorkshopFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Compete"
            1 -> "Learn"
            else -> null
        }
    }

    override fun getCount(): Int {
        return NUMBER_OF_TABS
    }

    companion object {
        const val NUMBER_OF_TABS = 2
    }
}
package org.aparoksha.app19.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.aparoksha.app19.fragments.DevelopersFragment
import org.aparoksha.app19.fragments.SponsorsFragment
import org.aparoksha.app19.fragments.TeamFragment

class InfoViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TeamFragment()
            1 -> SponsorsFragment()
            2 -> DevelopersFragment()
            else -> DevelopersFragment()
        }
    }

    override fun getCount(): Int {
        return NUMBER_OF_TABS
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Team"
            1 -> "Sponsors"
            2 -> "Developers"
            else -> null
        }
    }

    companion object {
        const val NUMBER_OF_TABS = 3
    }
}
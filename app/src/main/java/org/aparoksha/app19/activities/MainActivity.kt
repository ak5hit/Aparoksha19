package org.aparoksha.app19.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app19.R
import org.aparoksha.app19.equalsFragment
import org.aparoksha.app19.fragments.EventsFragment
import org.aparoksha.app19.fragments.HomeFragment
import org.aparoksha.app19.fragments.InfoFragment
import org.aparoksha.app19.fragments.UpdatesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFragment = HomeFragment()
        loadFragment(HomeFragment())
        initiateBottomNavigation()
    }

    private fun initiateBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.navigation_events -> {
                    loadFragment(EventsFragment())
                    true
                }
                R.id.navigation_updates -> {
                    loadFragment(UpdatesFragment())
                    true
                }
                R.id.navigation_info -> {
                    loadFragment(InfoFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        if (!currentFragment.equalsFragment(fragment)) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment)
            currentFragment = fragment
            transaction.commit()
        }
    }
}

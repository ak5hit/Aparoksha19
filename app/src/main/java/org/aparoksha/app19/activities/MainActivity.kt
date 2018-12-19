package org.aparoksha.app19.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app19.R
import org.aparoksha.app19.fragments.HomeFragment
import org.aparoksha.app19.fragments.InfoFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Home"
        loadFragment(HomeFragment())
        initiateBottomNavigation()
    }

    private fun initiateBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    supportActionBar!!.title = "Home"
                    true
                }
                R.id.navigation_events -> {
                    supportActionBar!!.title = "Events"
                    true
                }
                R.id.navigation_updates -> {
                    supportActionBar!!.title = "Updates"
                    true
                }
                R.id.navigation_info -> {
                    loadFragment(InfoFragment())
                    supportActionBar!!.title = "Info"
                    true
                }
                else -> {
                    supportActionBar!!.title = "Hey!! :D"
                    true
                }
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

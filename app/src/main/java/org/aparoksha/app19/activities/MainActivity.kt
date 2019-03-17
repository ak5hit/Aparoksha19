package org.aparoksha.app19.activities

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.StateListAnimator
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.aparoksha.app19.R
import org.aparoksha.app19.equalsFragment
import org.aparoksha.app19.fragments.*

class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment = Fragment()
    lateinit var toolBar : AppBarLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolBar = app_bar
        supportActionBar?.setDisplayShowTitleEnabled(false)
        loadFragment(HomeFragment())
        initiateBottomNavigation()
    }

    private fun initiateBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                }
                R.id.navigation_events -> {
                    loadFragment(EventsFragment())
                }
                R.id.navigation_updates -> {
                    loadFragment(UpdatesFragment())
                }
                R.id.navigation_info -> {
                    loadFragment(InfoFragment())
                }
            }
            true
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

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        toolbar_title.text = title.toString()
    }
}

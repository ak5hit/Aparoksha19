package org.aparoksha.app19

import android.support.v4.app.Fragment
import org.aparoksha.app19.fragments.HomeFragment
import org.aparoksha.app19.fragments.InfoFragment

fun Fragment.equalsFragment(fragment: Fragment): Boolean {
    return this is HomeFragment && fragment is HomeFragment || this is InfoFragment && fragment is InfoFragment
}
package org.aparoksha.app19.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_apk_month.*

import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.ApkMonthPagerAdapter

class ApkMonthFragment : Fragment() {

    private lateinit var mViewPager: ApkMonthPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mViewPager = ApkMonthPagerAdapter(activity!!.supportFragmentManager)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apk_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apk_month_pager.adapter = mViewPager
        apk_month_tabs.setupWithViewPager(apk_month_pager)
    }
}

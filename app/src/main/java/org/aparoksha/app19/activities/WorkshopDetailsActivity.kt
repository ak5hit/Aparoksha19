package org.aparoksha.app19.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_workshop_details.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.WorkshopDayAdapter
import org.aparoksha.app19.models.Day
import org.aparoksha.app19.models.Workshop
import org.aparoksha.app19.utils.AppDB

class WorkshopDetailsActivity : AppCompatActivity() {

    private lateinit var appDB: AppDB

    private lateinit var mWorkshop: Workshop
    private lateinit var mDays: ArrayList<Day>
    private lateinit var mWorkshopDayAdapter: WorkshopDayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workshop_details)

        appDB = AppDB.getInstance(this)

        val workshopID = intent.getStringExtra("workshopID")
        if (workshopID != null) {
            appDB.getAllWorkshops().forEach { if (it.id == workshopID) mWorkshop = it }
            mDays = mWorkshop.days
            mWorkshopDayAdapter = WorkshopDayAdapter(this, mDays)
        }
        days_grid.apply {
            layoutManager = GridLayoutManager(this@WorkshopDetailsActivity, 2)
            adapter = mWorkshopDayAdapter
        }
    }
}

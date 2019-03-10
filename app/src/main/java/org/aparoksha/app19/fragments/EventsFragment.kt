package org.aparoksha.app19.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.fragment_events.*

import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.CategoryAdapter
import org.aparoksha.app19.models.Event
import org.aparoksha.app19.viewmodels.EventsViewModel


class EventsFragment : Fragment() {
    private val TAG = EventsFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }
    private lateinit var adapter: CategoryAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        categoryRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)

        adapter = CategoryAdapter()
        categoryRecyclerView.adapter = adapter

        val allEvents = ViewModelProviders.of(this.activity!!).get(EventsViewModel::class.java)
        allEvents.getData(false)
        allEvents.allEvents.observe(this, Observer {
            it?.let {
                val list = ArrayList<Event>()
                for(element in it)
                    list.add(element)

                adapter.updateEvents(list)
            }
        })
    }
}

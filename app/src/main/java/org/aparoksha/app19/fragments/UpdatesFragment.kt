package org.aparoksha.app19.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.fragment_updates.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.NotificationAdapter
import org.aparoksha.app19.models.Notification


class UpdatesFragment : Fragment() {
    private lateinit var adapter : NotificationAdapter
    private val TAG = UpdatesFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.updates_fragment_title)

        val ref = FirebaseDatabase.getInstance().getReference("notifications")
        val query: Query = ref
        val options = FirebaseRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification::class.java)
                .build()

        adapter = NotificationAdapter(options, noNotifsTV)

        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

package org.aparoksha.app19.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_sponsors.*
import kotlinx.android.synthetic.main.fragment_updates.*
import org.aparoksha.app19.R
import org.aparoksha.app19.adapters.NotificationAdapter
import org.aparoksha.app19.models.Notification
import org.aparoksha.app19.models.Sponsor
import org.jetbrains.anko.toast


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

        adapter = NotificationAdapter(noNotifsTV)

        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerview.adapter = adapter

        //noNotifsTV.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE

        val db = FirebaseFirestore.getInstance()
        val ref = db.collection("verifiedNotifs")

        ref.addSnapshotListener { snapshot, exception ->
            if(exception != null) {
                Log.w(TAG, "Listen failed", exception)
                return@addSnapshotListener
            }
            if(snapshot == null) return@addSnapshotListener
            for(dc in snapshot.documentChanges) {
                when(dc.type) {
                    DocumentChange.Type.ADDED -> {
                        val notification = dc.document.toObject(Notification::class.java)
                        adapter.add(notification)
                        Log.d(TAG, notification.title)
                    } else -> return@addSnapshotListener
                }
            }
        }
    }
}

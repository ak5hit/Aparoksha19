package org.aparoksha.app19.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import org.aparoksha.app19.R


class HomeFragment : Fragment() {

    lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=FirebaseAuth.getInstance();
        Picasso.with(activity!!.applicationContext)
                .load(auth.currentUser!!.photoUrl)
                .resize(50, 50)
                .centerCrop()
                .into(userIm)
        username.setText(auth.currentUser!!.displayName)
        userid.setText(auth.currentUser!!.email)

    }

}

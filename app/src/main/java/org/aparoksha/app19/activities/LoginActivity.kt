package org.aparoksha.app19.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.aparoksha.app19.R
//import org.aparoksha.app19.utils

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    internal val RC_SIGN_IN = 101
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        GloginB.setOnClickListener {
            progBar.setVisibility(View.VISIBLE);
            signIn();
        }

    }

    fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        progBar.setVisibility(View.VISIBLE)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e("log", requestCode.toString());
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
             val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.e("log", task.toString());
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("TAG", "Google sign in failed", e)

            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.e("TAG", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.e("TAG", "signInWithCredential:success")
                        val user = mAuth.currentUser
//                        val u=UserDetail(mAuth)
                        progBar.setVisibility(View.GONE)
                        finish()
                        val intent:Intent= Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("TAG", "signInWithCredential:failure", task.exception)
                    }

                }
    }

    override fun onStart() {
        super.onStart()
        mAuth=FirebaseAuth.getInstance()
        if(mAuth.currentUser!=null){
            finish()
            val intent:Intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

}

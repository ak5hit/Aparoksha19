package org.aparoksha.app19.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import org.aparoksha.app19.R

//import org.aparoksha.app19.utils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)

        Handler().postDelayed({
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }, 5000)
    }
}

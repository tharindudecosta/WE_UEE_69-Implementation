package com.example.ayusri.Functions.Common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.ayusri.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var ivnote = findViewById<ImageView>(R.id.ivnote)

        ivnote.alpha = 0f
        ivnote.animate().setDuration(1700).alpha(1f).withEndAction{
            val  i = Intent(this, LoginUser::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}
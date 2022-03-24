package com.awesomecompany.mykinopoisk.view

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.awesomecompany.mykinopoisk.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME_OUT = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        (music_note.drawable as Animatable).start()

        Handler().postDelayed(
            {
                val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(i)
                finish()
            }, SPLASH_TIME_OUT
        )
    }
}
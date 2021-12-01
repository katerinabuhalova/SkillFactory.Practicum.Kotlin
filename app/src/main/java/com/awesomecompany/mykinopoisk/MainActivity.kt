package com.awesomecompany.mykinopoisk

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_menu.setOnClickListener {
            Toast.makeText(this, "Меню", Toast.LENGTH_LONG).show()
        }
        button2.setOnClickListener {
            Toast.makeText(this, "Избранное", Toast.LENGTH_LONG).show()
        }
        button3.setOnClickListener {
            Toast.makeText(this, "Подборки", Toast.LENGTH_LONG).show()
        }
        button4.setOnClickListener {
            Toast.makeText(this, "Настройки", Toast.LENGTH_LONG).show()
        }
        button5.setOnClickListener {
            Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_LONG).show()
        }
    }
}
package com.example.akshay.reportal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        logout.setOnClickListener {
            Toast.makeText(applicationContext, "Come back soon!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        about.setOnClickListener {
            Toast.makeText(applicationContext, "Achievement Unlocked: SEEKER", Toast.LENGTH_LONG).show()
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }
    }
}

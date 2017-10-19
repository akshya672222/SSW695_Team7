package com.example.akshay.reportal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        txtUserName.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            ptuserName.visibility = View.VISIBLE
        }

        txtPassword.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            ptPassword.visibility = View.VISIBLE
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}

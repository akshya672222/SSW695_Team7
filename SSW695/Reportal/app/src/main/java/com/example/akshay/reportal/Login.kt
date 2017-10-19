package com.example.akshay.reportal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*



class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logInBtn.setOnClickListener {
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
            intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        forgotPassword.setOnClickListener {
            intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
}

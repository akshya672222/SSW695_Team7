package com.example.akshay.reportal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registration.*


class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerBtn.setOnClickListener {
//            val intent = Intent(applicationContext, MainActivity.class)
//            startActivity(intent)
        }
    }

}

package com.example.akshay.reportal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_registration.*


class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
//        println("hello started the activity")
        println("hello came here..")
        registerBtn.setOnClickListener {
            println("hello")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}

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

//        if (getIntent().getBooleanExtra("EXIT", false)) {
//            println("came here")
//            finish();
//        }

        registerBtn.setOnClickListener {
            val intent = Intent(this, PostIssueActivity::class.java)
            startActivity(intent)
        }
    }

}

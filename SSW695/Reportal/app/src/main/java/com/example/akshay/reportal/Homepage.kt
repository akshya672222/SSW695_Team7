package com.example.akshay.reportal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*



class Homepage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)



        btnFeed.setOnClickListener {
            intent = Intent(this, Feed::class.java)
            //val bal:String = intent.getStringExtra("resultArray") as String
            //println("!!!!!!@#$%^&*()(*&^%$#@!@#$%^&*( "+ bal)
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            intent = Intent(this, History::class.java)
            intent.getStringExtra("resultArray")
            startActivity(intent)
        }
        btnReport.setOnClickListener {
            intent = Intent(this, PostIssueActivity::class.java)
            intent.getStringExtra("resultArray")
            startActivity(intent)
        }
        btnSettings.setOnClickListener {
            intent = Intent(this, Settings::class.java)
            intent.getStringExtra("resultArray")
            startActivity(intent)
        }

    }
}

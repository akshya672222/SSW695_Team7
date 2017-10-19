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
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            intent = Intent(this, History::class.java)
            startActivity(intent)
        }
        btnReport.setOnClickListener {
            intent = Intent(this, PostIssueActivity::class.java)
            startActivity(intent)
        }
        btnSettings.setOnClickListener {
            intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

    }
}

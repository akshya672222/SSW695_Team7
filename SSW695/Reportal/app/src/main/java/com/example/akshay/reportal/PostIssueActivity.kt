package com.example.akshay.reportal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post_issue.*
import android.content.Intent
import android.util.Log

class PostIssueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_issue)
        val intent = Intent(this, MainActivity::class.java)
        buttonCamera.setOnClickListener {
            startActivity(intent)
        }
        val intentToHistory = Intent(this, historyActivity::class.java)
        buttonGallery.setOnClickListener {
            startActivity(intentToHistory)
        }
    }
}

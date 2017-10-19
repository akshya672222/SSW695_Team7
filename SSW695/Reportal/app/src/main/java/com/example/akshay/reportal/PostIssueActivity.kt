package com.example.akshay.reportal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post_issue.*
import android.content.Intent

class PostIssueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_issue)
        val intent = Intent(this, PreviewSubmission::class.java)
        buttonCamera.setOnClickListener {
            startActivity(intent)
        }
        val intentToHistory = Intent(this, History::class.java)
        buttonGallery.setOnClickListener {
            startActivity(intentToHistory)
        }
    }
}

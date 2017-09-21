package com.example.akshay.reportal

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonPost.setText("POST")
    }

}

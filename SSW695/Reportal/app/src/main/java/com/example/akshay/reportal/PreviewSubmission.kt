package com.example.akshay.reportal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_preview_submission.*
import android.content.Intent
import android.view.WindowManager


class PreviewSubmission : AppCompatActivity() {

    //String [] nameCategories = {"Low", "Medium", "High"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_submission)
        buttonPost.setText("POST")
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        val intent = Intent(this, Registration::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        buttonPost.setOnClickListener {
            startActivity(intent)
        }
    }

}

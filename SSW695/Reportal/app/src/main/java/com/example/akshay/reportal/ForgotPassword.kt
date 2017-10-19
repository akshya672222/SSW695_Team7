package com.example.akshay.reportal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_forgotpassword.*



class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)


        forgotPasswordbtn.setOnClickListener {
            // Toast.makeText(applicationContext, "Link to reset the password has been sent to your email address", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, PreviewSubmission::class.java)
            startActivity(intent)
        }
    }

}

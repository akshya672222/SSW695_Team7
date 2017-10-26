package com.example.akshay.reportal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_registration.*


class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        val alertDialog = AlertDialog.Builder(this ).create()

        forgotPasswordbtn.setOnClickListener {
            // Toast.makeText(applicationContext, "Link to reset the password has been sent to your email address", Toast.LENGTH_SHORT).show()
            val email: String = email.text.toString()
            if (email.isEmpty()) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Email ID cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (!isEmailValid(email)) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please use Stevens email")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else {
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
            }
        }
    }
    fun isEmailValid(email: String): Boolean {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(email.indexOf("@stevens.edu")>0) {
                return true;
            }
        }
        return false
    }
}

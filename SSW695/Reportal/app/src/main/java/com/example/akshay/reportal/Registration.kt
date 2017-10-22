package com.example.akshay.reportal

/**
 * Created by Rafif on 10/06/2017.
   Created by sonal on 10/19/2017.
 */
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgotpassword.*
import kotlinx.android.synthetic.main.activity_registration.*


class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnRegister.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this ).create()
            val fname: String = first_name.text.toString()
            val lname: String = last_name.text.toString()
            val email: String = emailid.text.toString()
            val password: String = password.text.toString()
            val confPassword: String = confirm_password.text.toString()
            if (fname.length == 0 || lname.length == 0 || email.length == 0) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Fields cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (password.length >= 8 || password.length == 0){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Password should be between 0-8 chareactar")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (confPassword.length != password.length){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Password not matched")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else{
                Toast.makeText(applicationContext, "Registered successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
            }
        }
    }
}
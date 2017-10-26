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
            if (fname.length == 0) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("First Name cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (lname.length == 0) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Last Name cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (email.length == 0) {
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
            else if (password.length == 0){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Password cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (password.length < 8){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Password should have at least 8 characters")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (confPassword.length == 0){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Password cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (confPassword.length < 8){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Password should have at least 8 characters")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (confPassword != password){
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

    fun isEmailValid(email: String): Boolean {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(email.indexOf("@stevens.edu")>0) {
                return true;
            }
        }
        return false
    }
}
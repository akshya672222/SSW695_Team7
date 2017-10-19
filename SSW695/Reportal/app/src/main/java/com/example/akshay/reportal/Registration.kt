package com.example.akshay.reportal

/**
 * Created by sonal on 10/19/2017.
 */
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registration.*

class Registration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        btnRegister.setOnClickListener {

            if (first_name.toString().length == 0 || last_name.toString().length == 0 || emailid.toString().length == 0 )
                textViewMessage.text = "Field cannot be empty!"
            else if (password.toString().length >= 8 || password.toString().length == 0)
                textViewMessage.text = "password.toString().word should be between 0-8 chareactar"
            else if (confirm_password.toString().length != password.toString().length)
                textViewMessage.text = "password.toString().word not matched"
            else{
                textViewMessage.text = "$first_name $last_name Your are registered!"
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
            }


        }
    }
}
package com.example.rafifarab.reportal_registration

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var fname: EditText
    lateinit var lname: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var conf_password: EditText
    lateinit var register: Button
    lateinit var textViewMessage: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fname = findViewById(R.id.fname)
        lname = findViewById(R.id.lname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        conf_password = findViewById(R.id.conf_password)
        register = findViewById(R.id.register)
        textViewMessage = findViewById(R.id.textViewMessage)

        register.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val first_name = fname.text
        val last_name = lname.text
        val email = email.text
        val pass = password.text
        val conf_pass = conf_password.text

        if (first_name.length == 0 || last_name.length == 0 || email.length == 0 )
            textViewMessage.text = "Field cannot be empty!"
        else if (pass.length >= 8 || pass.length == 0)
            textViewMessage.text = "Password should be between 0-8 chareactar"
        else if (conf_pass.length != pass.length)
            textViewMessage.text = "Password not matched"
        else
            textViewMessage.text = "$first_name $last_name Your are registered!"


    }
}

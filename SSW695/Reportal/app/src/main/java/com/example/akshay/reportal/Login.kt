package com.example.akshay.reportal

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class Login : AppCompatActivity() {
    private var mAuthTask: UserLoginTask? = null
    //internal lateinit var userName: EditText
    //internal lateinit var mPasswordView:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logInBtn.setOnClickListener {
            attemptLogin()
        }
        btnRegister.setOnClickListener {
            intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        forgotPassword.setOnClickListener {
            intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }
    }
    fun attemptLogin(){
        if (mAuthTask != null) {
            return
        }
        // Reset errors.

        // Store values at the time of the login attempt.
        val email: String  = userName.text.toString()
        val password: String = password.text.toString()
        val alertDialog = AlertDialog.Builder(this ).create()
        var cancel = false
        var focusView: View? = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            alertDialog.setTitle("Error!")
            alertDialog.setMessage("Email cannot be empty!")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
            })
            alertDialog.show()
        } else if (!isEmailValid(email)) {
            alertDialog.setTitle("Error!")
            alertDialog.setMessage("Please use Stevens email")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
            })
            alertDialog.show()
        }
        // Check for a valid password, if the user entered one.
        else if (TextUtils.isEmpty(password)){
            alertDialog.setTitle("Error!")
            alertDialog.setMessage("Password cannot be empty!")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
            })
            alertDialog.show()
        }
        else if(!isPasswordValid(password)) {
            //password.setError(getString(R.string.error_invalid_password))
            //focusView = password
            alertDialog.setTitle("Error!")
            alertDialog.setMessage("Password should have at least 8 characters!")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
            })
            alertDialog.show()
        } else{
            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
        }
    }
    fun isPasswordValid(password: String): Boolean {
        if (password.length >= 8)
            return true
        else
            return false
    }

    fun isEmailValid(email: String): Boolean {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(email.indexOf("@stevens.edu")>0) {
                return true;
            }
        }
        return false
    }

    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                val responseCode = connect()
                if (responseCode == 200) {
                    println("Here I come")
                    val intent = Intent(applicationContext, Homepage::class.java)
                    startActivity(intent)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            println("I am here in onPostExecute")
            mAuthTask = null
        }

        override fun onCancelled() {
            mAuthTask = null
        }

        @Throws(IOException::class, JSONException::class)
        private fun connect(): Int {
            val a = 1
            return a
        }

    }
}


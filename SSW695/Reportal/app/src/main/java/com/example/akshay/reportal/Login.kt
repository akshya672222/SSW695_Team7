package com.example.akshay.reportal

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
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
    internal lateinit var userName: EditText
    internal lateinit var mPasswordView:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logInBtn.setOnClickListener {

            attemptLogin()

            val intent = Intent(this, Homepage::class.java)
            startActivity(intent)
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
        //mPasswordView.setError(" ")
        // Store values at the time of the login attempt.
        val email = userName.text.toString()
        val password = mPasswordView.getText().toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password))
            focusView = mPasswordView
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            userName.error = getString(R.string.error_field_required)
            focusView = userName
            cancel = true
        } else if (!isEmailValid(email)) {
            userName.error = getString(R.string.error_invalid_email)
            focusView = userName
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            UserLoginTask(email, password).execute(null as Void?)
        }
    }

    fun isPasswordValid(password: String): Boolean {
        if (password.length > 4)
            return true
        else
            return false
    }

    fun isEmailValid(email: String): Boolean {
        if(email.length>50 || email.indexOf("stevens.edu")<0){
            return false;
        }
        if(email.contains("@"))
            return false;
        return true
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


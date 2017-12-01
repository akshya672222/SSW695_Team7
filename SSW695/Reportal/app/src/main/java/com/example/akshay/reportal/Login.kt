package com.example.akshay.reportal

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Patterns
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
import java.io.InputStreamReader
import java.io.BufferedReader
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import com.example.akshay.reportal.R.string.post
import android.widget.TextView
import android.R.id.edit
import android.content.SharedPreferences
import java.io.Serializable
import android.content.Context
import android.os.Message
import android.app.Activity

//import javax.swing.UIManager.put

class Login : AppCompatActivity() {
    val context:Context = this
    private var mAuthTask: UserLoginTask? = null
    //internal lateinit var userName: EditText
    //internal lateinit var mPasswordView:EditText
    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            val crypt = CryptLib()
            var output = ""
            val plainText = "123456"
            val key = "n3x3K25Cn4BiNEhg3Ahn14CQG0ez8uju" //32 bytes = 256 bit
            val iv = "3C4549AA4F6B2A6F" //16 bytes = 128 bit
            output = crypt.encrypt(plainText, key, iv) //encrypt
            System.out.println("encrypted text=" + output);
            output = crypt.decrypt(output, key, iv) //decrypt
            System.out.println("decrypted text=" + output);
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

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
        val checkEmail = "csakhile@stevens.edu"
        val checkPassword = "12345678"
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
        }
        else{
            //Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            //val intent = Intent(this, Homepage::class.java)
            //startActivity(intent)
            System.out.println("entering user login task")
            UserLoginTask(email,password).execute()

        }

    }


    fun isPasswordValid(password: String): Boolean {
        if (password.length >= 8)
            return true
        else
            return false
    }

    fun isEmailValid(email: String): Boolean {

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(email.indexOf("@stevens.edu")>0) {
                return true;
            }
        }
        return false
    }

    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) : AsyncTask<Void, Void, Boolean>(), Serializable{

        //System.out.println("The result is1 for allEvnets: "+conn.getResponseMessage());
                //can call this instead of con.connect()
                //System.out.println("Error occured in errorStream");
                //System.out.println("The result is5: " + line);
                //System.out.println("123accessing AllEvents"+builder);

        val allEvents: Int

            @Throws(IOException::class, JSONException::class)
            get() {
                System.out.println("Exception thrown")
                val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/login")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("Content-Type", "application/json")
                val responseCode = conn.responseCode
                if (responseCode >= 400 && responseCode <= 499) {
                    System.out.println("if in exception")
                    val br = BufferedReader(InputStreamReader(conn.errorStream))
                    val builder = StringBuilder()
                    for (line in br.readLine()){
                        while (line != null) {builder.append(line + "\n") }
                    }
                    br.close()
                } else {
                    System.out.println("else in exception")
                    val br = BufferedReader(InputStreamReader(conn.inputStream))
                    val builder = StringBuilder()
                    for (line in br.readLine()){
                        while (line != null) {builder.append(line + "\n") }
                    }
                    val allEventsJsonBlock = JSONObject(builder.toString())
                    val allEventsArray = allEventsJsonBlock.getJSONArray("events_list")
                    br.close()
                }
                return conn.responseCode
            }

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt   against a network service.
            System.out.println("background")
            val message: String

            try {
                System.out.println("background try block")
                val conn = connect()
                val responseCode = conn.responseCode
                println("I am here in doInBackground " + responseCode)
                if (responseCode == 200) {

                    System.out.println("Login Successfull");
                    val sp = getSharedPreferences("Login", 0)
                    val editor = sp.edit()
                    editor.putString("email", mEmail)
                    editor.putString("password", mPassword)
                    editor.putString("isLoggedIn", "True")
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^getting login info");
                    editor.commit()
                    val USER_NAME = mEmail
                    //code to connect to server and get events
                    val br = BufferedReader(InputStreamReader(conn.inputStream))
                    val builder = StringBuilder()
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^getting buffer reader info     "+br.toString());
                   // val line = br.readLine()
                  ""

                  //  val reader = BufferedReader(reader)
                    var line: String? = null;
                    while ({ line = br.readLine(); line }() != null) {
                        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^line    "+line);
                        builder.append(line + "\n");
                    }
                    br.close()

                    val resultJsonBlock = JSONObject(builder.toString())
                    val status_code:String
                    System.out.println("^^^^^^^^^^^^^^^^************************ resultJsonBlock"+resultJsonBlock);

                    System.out.println("^^^^^^^^^^^^^^^^************************ resultJsonBlock.toString()"+resultJsonBlock.toString());

                    if(resultJsonBlock.toString().contains("status_code")){
                        //System.out.println("^^^^^^^^^^^^^^^^************************ here  in if"+status_code);

                        status_code= resultJsonBlock.get("status_code").toString()
                        System.out.println("^^^^^^^^^^^^^^^^************************ here "+status_code);
                        //val alertDialog = AlertDialog.Builder(context).create()
                        System.out.println("^^^^^^^^^^^^^^^^************************ after dialogue "+status_code);
                        if(status_code == "400") {
                            message = resultJsonBlock.get("message").toString()

                            System.out.println(context)

                            object : Thread() {

                                    fun onMainThread(runnable: Runnable) {
                                        val mainHandler = Handler(Looper.getMainLooper())
                                        mainHandler.post(runnable)
                                        Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
                                        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+message)
                                    }

                            }.start()

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show()

                        }
                        else if(status_code == "200") {
                            System.out.println("^^^^^^^^^^^^^^^^************************ in 200  "+status_code);
                            val categories = resultJsonBlock.get("categories").toString()
                            val priorities = resultJsonBlock.get("priorities").toString()
                            val result = resultJsonBlock.get("result").toString()
                            intent.putExtra("categoriesArray",categories)
                            intent.putExtra("prioritiesArray",priorities)
                            intent.putExtra("resultArray",result)
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+categories)
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+priorities)
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+result)

                            val intent = Intent(applicationContext, Homepage::class.java)

                            startActivity(intent)
                        }
                    }

                    //System.out.println("^^^^^^^^^^^^^^^^************************ here "+status_code);

                    System.out.println("Printing json block array: "+ resultJsonBlock.getJSONArray("Subscription"));


                    val intent = Intent(applicationContext, Homepage::class.java)

                    startActivity(intent)
                }
                /*
                else if (responseCode >= 400 && responseCode <= 499) {
                    val br = BufferedReader(InputStreamReader(conn.errorStream))
                    val builder = StringBuilder()
                    for (line in br.readLine()){
                        while (line != null) {builder.append(line + "\n") }
                    }
                    br.close()
                    //this is to make UI changes which are running on main thread
                    val mainHandler = Handler(applicationContext.mainLooper)
                    val myRunnable = object : Runnable {
                        internal var firstKeyDown = false
                        override fun run() {
                            val userName = findViewById<View>(R.id.userName) as EditText
                            val password = findViewById<View>(R.id.password) as EditText
                            //val errorMsg = findViewById(R.id.errMsg) as TextView
                            userName.requestFocus()
                            userName.setText("")
                            password.setText("")
                            //errorMsg.text = "Wrong Credentials. Please Try again."
                            System.out.println("firstKeyDown here 1 "+ firstKeyDown);
                            System.out.println("In here 1 "+ firstKeyDown);
                            if (!firstKeyDown) userName.setOnKeyListener(object : View.OnKeyListener {

                                override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                                    System.out.println("fin here 2 "+ firstKeyDown);
                                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                                        //errorMsg.text = ""
                                        firstKeyDown = true
                                        return false
                                    }
                                    return true
                                }
                            })
                        } // This is your code
                    }
                    mainHandler.post(myRunnable)
                }
                */
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // TODO: register the new account here.
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            /* System.out.println("I am here in onPostExecute");
            loginTask = null;*/
        }

        override fun onCancelled() {
            /*loginTask = null;*/
        }

        @Throws(IOException::class, JSONException::class)
        private fun connect(): HttpURLConnection {
            System.out.println("I am here in connect");
            val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/login")
            //URL url = new URL("http://34.208.210.71/login");
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true
            val requestObject = JSONObject()
            requestObject.put("email", mEmail)
            requestObject.put("password", mPassword)
            val os = conn.outputStream
            val bw = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            System.out.println(requestObject);
            bw.write(requestObject.toString())
            bw.close()
            os.close()
            conn.connect()
            System.out.println("The result is1: "+conn.getResponseMessage());
            return conn
        }

    }
}


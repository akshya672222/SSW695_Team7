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
import org.json.JSONObject
import org.json.JSONException
import android.widget.EditText
import android.os.AsyncTask
import java.io.IOException
import java.net.URL
import java.net.HttpURLConnection
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.InputStreamReader
import java.io.BufferedReader
import java.net.MalformedURLException;

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
            if (fname.isEmpty()) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("First Name cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (lname.isEmpty()) {
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Last Name cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (email.isEmpty()) {
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
            else if (password.isEmpty()){
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
            else if (confPassword.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Confirm Password cannot be empty!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (confPassword.length < 8){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Confirm Password should have at least 8 characters")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if (confPassword != password){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Passwords do not match")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else{
                UserRegistrationTask(fname,lname,password,email).execute()

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

    internal inner class UserRegistrationTask(private val firstName: String, private val lastName: String, private val password: String, private val email: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                val code = register()
                if(code.equals("OK")) {
                    Toast.makeText(applicationContext, "Registered successfully", Toast.LENGTH_LONG).show()
                    //val intent = Intent(this, Homepage::class.java)
                    //startActivity(intent)

                    val intent = Intent(applicationContext, Homepage::class.java)

                    startActivity(intent)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }


        @Throws(IOException::class, JSONException::class)
        private fun register(): Int {
            println("------------------------------------------------------I am here in register")
            val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/register")
            val conn = url.openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")


            val requestObject = JSONObject()
            requestObject.put("email", email)
            requestObject.put("password", password)
            requestObject.put("firstname", firstName)
            requestObject.put("lastname", lastName)
            requestObject.put("type", 1)
            requestObject.put("status", 1)
            requestObject.put("category", 1)

            println("------------------------------------------------------I am here in connect 1234")

            val os =  conn.outputStream
            val bw = BufferedWriter(OutputStreamWriter(os, "UTF-8"))

            println(requestObject)

            bw.write(requestObject.toString())
            bw.close()
            os.close()

            val br = BufferedReader(InputStreamReader(conn.inputStream))
            val builder = StringBuilder()

            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+br.toString())
            var line: String = br.readLine()
            while (line != null){
            builder.append(line + "\n")

                   println("in while ---------------------------"+line)
                line = br.readLine()
                if line.startsWith() {}

            }
            println(JSONObject(builder.toString()))
            val responseBody = JSONObject(builder.toString())


            System.out.println("The result from server...."+responseBody)

            val resultJsonBlock = JSONObject(builder.toString())
            val status_code:String
            System.out.println("^^^^^^^^^^^^^^^^************************ resultJsonBlock"+resultJsonBlock);

            System.out.println("^^^^^^^^^^^^^^^^************************ resultJsonBlock.toString()"+resultJsonBlock.toString());

            if(resultJsonBlock.toString().contains("status_code")){

                status_code= resultJsonBlock.get("status_code").toString()
                System.out.println("^^^^^^^^^^^^^^^^************************ here "+status_code);}

            val responseCode = conn.getResponseCode()
            if(responseCode.equals("OK")) {
                Toast.makeText(applicationContext, "Registered successfully", Toast.LENGTH_LONG).show()
                //val intent = Intent(this, Homepage::class.java)
                //startActivity(intent)

                val intent = Intent(applicationContext, Homepage::class.java)

                startActivity(intent)
            }

            return responseCode

        }

        fun attemptConnection() {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {
                val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/")

                connection = url.openConnection() as HttpURLConnection
                connection!!.connect()

                val stream = connection!!.getInputStream()
                reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()
                var line = " "

                for (line in reader.readLine()){
                    while (line != null) { buffer.append(line); }
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (connection != null) {
                    connection!!.disconnect()
                }
                try {
                    if (reader != null) {
                        reader!!.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
    }


}


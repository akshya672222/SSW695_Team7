package com.example.akshay.reportal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_preview_submission.*
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.time.LocalDateTime


class PreviewSubmission : AppCompatActivity() {

    //String [] nameCategories = {"Low", "Medium", "High"}



    override fun onCreate(savedInstanceState: Bundle?) {

        /*if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                var newString:String = ""
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ newString "+ newString)
            } else {
                var newString:String = extras["resultArray"] as String
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ newString "+newString)
            }
        } else {
            var newString:String  = savedInstanceState.getSerializable("resultArray") as String
            println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ newString "+newString)
        }*/

        super.onCreate(savedInstanceState)
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!! Im in preview Submission")
        setContentView(R.layout.activity_preview_submission)
        buttonPost.setText("POST")
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        //val ArrayExtra = intent.extras["resultArray"] as String
        //val ArrayExtra:String = intent.getStringExtra("ArrayExtra")
        //println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ArrayExtra " + ArrayExtra)

        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!! 1")
        val extra = intent.extras["image"] as Bitmap
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!! 2")
        imageViewPreviewPostIssue.setImageBitmap(extra)
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!! 3")
        buttonPost.setOnClickListener {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ in onclick")

            val alertDialog = AlertDialog.Builder(this ).create()
            val description: String = textViewDescription.text.toString()
            val location: String = textViewLocation.text.toString()
            val categorySelected: String = category.selectedItem.toString ()
            val prioritySelected: String = priority.selectedItem.toString ()

            if(description.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please enter Description!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {dialogInterface, i ->})
                alertDialog.show()
            }
            else if(location.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please enter Location!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {dialogInterface, i ->})
                alertDialog.show()
            }
            else if(categorySelected.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please select a category!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {dialogInterface, i ->})
                alertDialog.show()
            }
            else if(prioritySelected == ""){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please select a priority")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {dialogInterface, i ->})
                alertDialog.show()
            }
            else {
                //Toast.makeText(this, "Post Submitted", Toast.LENGTH_SHORT).show()
                //val intent = Intent(this, Homepage::class.java)
                //PostSubmission()
                //startActivity(intent)
                PostSubmission(extra,description,location,categorySelected,prioritySelected).execute()

            }

        }
    }

    inner class PostSubmission internal constructor(private val mMap: Bitmap, private val mDescription: String, private val mLocation: String, private val mCategorySelected: String, private val mPrioritySelected: String) : AsyncTask<Void, Void, Boolean>(), Serializable{

        //System.out.println("The result is1 for allEvnets: "+conn.getResponseMessage());
        //can call this instead of con.connect()
        //System.out.println("Error occured in errorStream");
        //System.out.println("The result is5: " + line);
        //System.out.println("123accessing AllEvents"+builder);


        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                val code = post()

            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return null
        }

        @Throws(IOException::class, JSONException::class)
        private fun post(): Int {
            println("------------------------------------------------------I am here in previewSubmission")
            val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/post_issue")
            val conn = url.openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")

            //val current = LocalDateTime.now()















            val requestObject = JSONObject()
            requestObject.put("file", mMap)
            requestObject.put("user_id", password)
            requestObject.put("issue_lat", 40.1235)
            requestObject.put("issue_long", 23.232342)
            requestObject.put("issue_time", "11:10:43")
            requestObject.put("issue_description", 1)
            requestObject.put("issue_category_id", 1)
            requestObject.put("issue_cpriority", 1)

            println("------------------------------------------------------I am here in connect 1234")

            val os =  conn.outputStream
            val bw = BufferedWriter(OutputStreamWriter(os, "UTF-8"))

            println(requestObject)

            bw.write(requestObject.toString())
            bw.close()
            os.close()

            val br = BufferedReader(InputStreamReader(conn.inputStream))
            val builder = StringBuilder()
            var statusCode: Int = 0

            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+br.toString())
            //println(JSONObject(builder.toString()))
            var line: String = br.readLine()

            while ({ line = br.readLine(); line }() != null) {
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^line    "+line);
                val separate2 = line.split(":","  ")
                println(separate2)

                println(separate2.size)
                if(line.contains("status_code")) {
                    if (line.contains("400")) {

                        println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& "+line)
                        object : Thread() {

                            /*fun onMainThread(runnable: Runnable) {
                                val mainHandler = Handler(Looper.getMainLooper())
                                mainHandler.post(runnable)
                                Toast.makeText(this@Registration, "Account already exists", Toast.LENGTH_SHORT).show()
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+"Account already exists!")
                            }*/

                            /*
                            fun onMainThread(runnable: Runnable) {
                                val mainHandler = Handler(Looper.getMainLooper())
                                mainHandler.post(runnable)
                            }

                            */
                        }.start()
                    }
                    else if (line.contains("200")) {
                        println ("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+line)
                        val intent = Intent(applicationContext, Homepage::class.java)
                        startActivity(intent)
                    }

                    break
                }

            }
            /*
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
            */
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

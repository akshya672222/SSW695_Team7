package com.example.akshay.reportal

/**
 * Created by AKSHAY on 05/10/17.
 */

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_history.*
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Feed : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)



        val listView =  findViewById<ListView>(R.id.listView)
//        val redColor= Color.parseColor("#FF0000")
//        listView.setBackgroundColor(redColor)

        //GetFeedTask().execute()

        listView.adapter= MyCustomAdapter(this)


    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {
        private val mContext: Context

        private val userNames= arrayListOf<String>(
                "Maha Alidrisi", "Sonali Patil","Pranay Bure","Pranay Bure"
        )
        private val titles= arrayListOf<String>(
                "Something wrong w/ AC","Broken Window", "Projector issues","Smart Board issue"
        )

        private val descriptions= arrayListOf<String>(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa."
        )


        private val Dates= arrayListOf<String>(
                "10/10/2017","10/5/2017", "9/22/2017","8/1/2017"
        )

        private val status= arrayListOf<String>(
                "open","closed", "on hold","closed"
        )

        init {
            mContext = context

        }
        //responsible for # of rows in myList
        override fun getCount(): Int {
            return titles.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "Test String"
        }



        //responsible for rendering each row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {


            val layoutInflater= LayoutInflater.from(mContext)
            val rowMAin= layoutInflater.inflate(R.layout.row_feed, viewGroup, false)

            val UserNamesTextView= rowMAin.findViewById<TextView>(R.id.userName_textview)
            val userNames= userNames.get(position)
            UserNamesTextView.text= "@$userNames"

            val titleTextView= rowMAin.findViewById<TextView>(R.id.title_textView)
            titleTextView.text= titles.get(position)

            val descriptionTextView= rowMAin.findViewById<TextView>(R.id.description_textview)
            descriptionTextView.text= descriptions.get(position)

            val statusTextView= rowMAin.findViewById<TextView>(R.id.status_textview)
            val status= status.get(position)
            statusTextView.text= "Status: $status"
            return rowMAin
//            val textView= TextView(mContext)
//            textView.text = "Here is my row"
//            return textView
        }

    }

    inner class GetFeedTask internal constructor() : AsyncTask<Void, Void, Boolean>(), Serializable {

        //System.out.println("The result is1 for allEvnets: "+conn.getResponseMessage());
        //can call this instead of con.connect()
        //System.out.println("Error occured in errorStream");
        //System.out.println("The result is5: " + line);
        //System.out.println("123accessing AllEvents"+builder);


        val allEvents: Int


            @Throws(IOException::class, JSONException::class)
            get() {
                println("")
                println("!!!!@#$%^&*()(*&^%$#@!@#$%^&*()(*&^%$#@!@#$%^&*(*&^%$#@#$%^&*(*&^%$#@#$%^&*()(*&^%$#@#$%^&*(*&^%$#@#$%^&*")

                println("In getFeed")
                val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/get_issue")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("Content-Type", "application/json")
                val responseCode = conn.responseCode
                println("response code: "+responseCode)
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
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ background")
            val message: String

            try {
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&background try block")
                val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/get_issue")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.setRequestProperty("Content-Type", "application/json")
                val responseCode = conn.responseCode
                println("I am here in doInBackground of getFeed" + responseCode)
                if (responseCode == 200) {

                    System.out.println("GetFeed Successfull");
                    val sp = getSharedPreferences("Feed", 0)
                    val editor = sp.edit()
                   /* editor.putString("email", mEmail)
                    editor.putString("password", mPassword)
                    */editor.putString("Getting Feed", "True")
                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^getting Feed info");
                    editor.commit()
                    //val USER_NAME = mEmail
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

                            /*System.out.println(context)

                            object : Thread() {

                                fun onMainThread(runnable: Runnable) {
                                    val mainHandler = Handler(Looper.getMainLooper())
                                    mainHandler.post(runnable)
                                    Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
                                    System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+message)
                                }

                            }.start()*/

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

                    val intent = Intent(applicationContext, Homepage::class.java)

                    startActivity(intent)
                }

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
            System.out.println("I am here in connect in get feed");
            val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/get_issue")
            //URL url = new URL("http://34.208.210.71/login");
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true
            val requestObject = JSONObject()
            /*requestObject.put("email", mEmail)
            requestObject.put("password", mPassword)*/
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
package com.example.akshay.reportal

/**
 * Created by AKSHAY on 05/10/17.
 */

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_history.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
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

    fun attemptConnection() {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null

        try {
            val url = URL("http://ec2-34-207-75-73.compute-1.amazonaws.com/api/register")

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
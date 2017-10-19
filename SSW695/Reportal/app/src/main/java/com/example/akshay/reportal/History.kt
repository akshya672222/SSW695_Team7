package com.example.akshay.reportal

/**
 * Created by AKSHAY on 05/10/17.
 */


import android.graphics.Color
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.history.*

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)

        val listView =  findViewById<ListView>(R.id.listView)
//        val redColor= Color.parseColor("#FF0000")
//        listView.setBackgroundColor(redColor)

        listView.adapter= MyCustomAdapter(this)


    }

    private class MyCustomAdapter(context: Context): BaseAdapter() {
        private val mContext: Context
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
            val rowMAin= layoutInflater.inflate(R.layout.row_main, viewGroup, false)

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

}
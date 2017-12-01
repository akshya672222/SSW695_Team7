package com.example.akshay.reportal

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post_issue.*
import kotlinx.android.synthetic.main.activity_preview_submission.*

/**
 * Created by sonal on 10/5/2017.
 */

class report : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mainViewHolder.get == null

        val extra = intent.extras["data"] as Bitmap
        imageViewPreviewPostIssue.setImageBitmap(extra)
        setContentView(R.layout.activity_preview_submission)
    }
}

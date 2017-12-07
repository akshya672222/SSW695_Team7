package com.coderzheaven.storeimage

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var btnSelectImage: FloatingActionButton
    lateinit var imgView: AppCompatImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        // Find the views...
        coordinatorLayout = (findViewById(R.id.coordinatorLayout) as CoordinatorLayout?)!!
        btnSelectImage = (findViewById(R.id.btnSelectImage) as FloatingActionButton?)!!
        imgView = (findViewById(R.id.imgView) as AppCompatImageView?)!!

        btnSelectImage.setOnClickListener(this)

    }

    /* Choose an image from Gallery */
    internal fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                val selectedImageUri = data.data
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    val path = getPathFromURI(selectedImageUri)
                    Log.i(TAG, "Image Path : " + path!!)
                    // Set the image in ImageView
                    imgView.setImageURI(selectedImageUri)
                }
            }
        }
    }

    /* Get the real path from the URI */
    fun getPathFromURI(contentUri: Uri): String? {
        var res: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    override fun onClick(v: View) {
        openImageChooser()
    }

    companion object {

        private val SELECT_PICTURE = 100
        private val TAG = "MainActivity"
    }

}

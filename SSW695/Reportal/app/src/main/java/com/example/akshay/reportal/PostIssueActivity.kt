package com.example.akshay.reportal

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post_issue.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import kotlinx.android.synthetic.main.activity_post_issue.*
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable


class PostIssueActivity : AppCompatActivity() {

    val CAMERA_REQUEST_CODE =0
    val MY_PERMISSIONS_REQUEST_CAMERA =0
    val MY_REQUEST_CAMERA = 10
    val MY_REQUEST_WRITE_CAMERA = 11
    val CAPTURE_CAMERA = 12

    val MY_REQUEST_READ_GALLERY = 13
    val MY_REQUEST_WRITE_GALLERY = 14
    val MY_REQUEST_GALLERY = 15

    var fileCamera: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_post_issue)
 //       val intent = Intent(this, MainActivity::class.java)
        buttonCamera.setOnClickListener {
           // checkPermissionCamera()

            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(callCameraIntent.resolveActivity(packageManager) != null){
                startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE)
            }
        }
 //       val intentToHistory = Intent(this, historyActivity::class.java)
        buttonGallery.setOnClickListener {
            //checkPermissionGallery()
        }
        adddescriptionbtn.setOnClickListener {
            val intent = Intent(applicationContext, PreviewSubmission::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("in Activity result")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+requestCode)

        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                println("in camera request code! ")
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+requestCode)
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+data)
                println("activity!!!!!!!!!!!! "+Activity.RESULT_OK)
                if(resultCode == Activity.RESULT_OK && data != null){
                    println("iN BITMAP CODE")
                    capturedImageView.setImageBitmap(data.extras.get("data") as Bitmap)
                    //capturedImageView.setImageDrawable(data.extras.get("data") as Drawable)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun hasPermissionInManifest(context: Context, permissionName: String): Boolean {
        val packageName = context.packageName
        try {
            val packageInfo = context.packageManager
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            val declaredPermisisons = packageInfo.requestedPermissions
            if (declaredPermisisons != null && declaredPermisisons.size > 0) {
                for (p in declaredPermisisons) {
                    if (p == permissionName) {
                        return true
                    }
                }
            }
        } catch (e: Exception) {

        }

        return false
    }

/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAPTURE_CAMERA ->
                    img1.setImageURI(Uri.parse("file:///"+fileCamera))
            MY_REQUEST_GALLERY -> try {
                val inputStream = contentResolver.openInputStream(data?.data)
                fileCamera = getFile()
                val fileOutputStream = FileOutputStream(fileCamera)
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (true){
                    bytesRead = inputStream.read(buffer)
                    if (bytesRead == -1) break;
                    fileOutputStream.write(buffer, 0, bytesRead)
                }
                fileOutputStream.close()
                inputStream.close()
                img1.setImageURI(Uri.parse("file:///"+fileCamera))
            }catch (e: Exception){
                Log.e("","Error while creating temp file", e)
            }
        }
    }

    //Method to check if camera permission are granted or not!
    private fun checkPermissionCamera(){
        val permissionCheck = ContextCompat.checkSelfPermission(this@PostIssueActivity, Manifest.permission.CAMERA)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@PostIssueActivity, arrayOf<String>(Manifest.permission.CAMERA), MY_REQUEST_CAMERA)
        }else{
            catchPhoto()
        }
    }

    private fun catchPhoto(){
        fileCamera = getFile()
        if (fileCamera != null){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                val photoUri = Uri.fromFile(fileCamera)
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, CAPTURE_CAMERA)
            }catch (e: ActivityNotFoundException){

            }
        }else{
            Toast.makeText(this@PostIssueActivity, "Please check your sdcard status", Toast.LENGTH_SHORT).show()
        }
    }

    //Method to check if gallery permission are granted or not!
    private fun checkPermissionGallery(){
        val permissionCheck = ContextCompat.checkSelfPermission(this@PostIssueActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@PostIssueActivity, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_REQUEST_WRITE_CAMERA)
        }else{
            checkPermissionCamera()
        }
    }

    fun getFile(): File? {
        val fileDir = File(""+Environment.getExternalStorageDirectory()+"/Android/data/"+applicationContext.packageName+"/Files")
        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                return null
            }
        }
        val mediaFile = File(fileDir.path + File.separator + "issue.jpg")
        return mediaFile
    }

    /** Check if this device has a camera  */
    private fun checkCameraHardware(context: Context): Boolean {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    /** A safe way to get an instance of the Camera object.  */
    fun getCameraInstance(): Camera? {
        var c: Camera? = null
        try {
            c = Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
        }
        return c // returns null if camera is unavailable
    }
*/
}

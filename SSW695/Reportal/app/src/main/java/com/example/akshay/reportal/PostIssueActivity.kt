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
import android.graphics.BitmapFactory
import android.R.attr.data
import java.io.FileNotFoundException
import java.nio.file.Files.write
import android.app.PendingIntent.getActivity
import android.support.v7.app.AlertDialog
import java.io.ByteArrayOutputStream


class PostIssueActivity : AppCompatActivity() {

    val CAMERA_REQUEST_CODE =0  //Request code when accessing camera for picture (permission granted)
    val GALLERY_REQUEST_CODE = 1    //Request code when accessing gallery for picture (permission granted)
    val MY_PERMISSIONS_REQUEST_CAMERA =0
    val MY_REQUEST_CAMERA = 10      //Request code for camera permission from user
    val MY_REQUEST_WRITE_CAMERA = 11
    val CAPTURE_CAMERA = 12

    val MY_REQUEST_READ_GALLERY = 13
    val MY_REQUEST_WRITE_GALLERY = 14
    val MY_REQUEST_GALLERY = 15

    var fileCamera: File? = null
    var imageCapturedBitmap : Bitmap? = null
    /*
    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), GALLERY_REQUEST_CODE)
        } else {
            write()
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var newString: String
        if (savedInstanceState == null) {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  in first if")
            val extras = intent.extras
            if (extras == null) {
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  in if with extras")
                newString = ""
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+ newString)
            } else {
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  in else with extras")
                newString = extras.getString("categoriesArray")
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! "+ newString)
            }
        } else {
            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  in last else of first if")
            newString = savedInstanceState.getSerializable("categoriesArray") as String
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+ newString)
        }


        setContentView(R.layout.activity_post_issue)
        buttonCamera.setOnClickListener {

            val permission = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)

            if (permission == PackageManager.PERMISSION_GRANTED){
                println("came here............Aks")
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                if(callCameraIntent.resolveActivity(packageManager) != null){
                    startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE)
                }
            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

                    println("In rational request block!!!!")
                    val someValue = Manifest.permission.CAMERA
                    var permissionList = Array<String>(1){someValue}
                    ActivityCompat.requestPermissions(this, permissionList,MY_REQUEST_CAMERA)


                }else{
                    println("in ration request else block!!!!!!!!!!!")
                    val alertDialog = AlertDialog.Builder(this ).create()
                    alertDialog.setTitle("Permission Required!")
                    alertDialog.setMessage("This application requires camera to capture image of the issues.\n" +
                            "For that we need camera permission.\n" +
                            "You can enable the camera permissions by following the steps:\n" +
                            "Settings-> Permissions-> Reportal-> Enable")

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                        dialogInterface, i ->
                    })
                    alertDialog.show()

//                    val someValue = Manifest.permission.CAMERA
//                    var permissionList = Array<String>(1){someValue}
//                    ActivityCompat.requestPermissions(this, permissionList,MY_REQUEST_CAMERA)
                }
            }

        }
        buttonGallery.setOnClickListener {

            val photoPickerIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            //val permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

            //val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE)
        }
        adddescriptionbtn.setOnClickListener {
            if (capturedImageView.drawable == null){
                val alertDialog = AlertDialog.Builder(this ).create()
                alertDialog.setTitle("Issue image!")
                alertDialog.setMessage("Please select a image to attach with issue!!")

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i ->
                })
                alertDialog.show()
            }else{
                println("IN on click!")
                val intent = Intent(applicationContext, PreviewSubmission::class.java)
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+ intent)
                intent.putExtra("image", imageCapturedBitmap)
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+ imageCapturedBitmap)
                println("hELLO!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@")
                intent.getStringExtra("resultArray")
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_REQUEST_CAMERA->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                    if(callCameraIntent.resolveActivity(packageManager) != null){
                        startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE)
                    }
                }
            }
            else ->{

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("in Activity result")
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + requestCode)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                println("in camera request code! ")
                println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + requestCode)
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + data)
                println("activity!!!!!!!!!!!! " + Activity.RESULT_OK)
                if (resultCode == Activity.RESULT_OK && data != null) {
                    println("iN BITMAP CODE")
                    if(capturedImageView.drawable == null){
                        println("image not present in view!!!")
                    }else{
                        println("image present over write it!!!")
                    }
                    imageCapturedBitmap = data.extras.get("data") as Bitmap
                    capturedImageView.setImageBitmap(imageCapturedBitmap)
                    //capturedImageView.setImageDrawable(data.extras.get("data") as Drawable)
                }
            }
            GALLERY_REQUEST_CODE -> {
                println("Im in the gallery!")

                if (resultCode === Activity.RESULT_OK && data != null) {
                    try {
                        println("in try block: "+data)
                        val imageUri = data?.getData()
                        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ imageUri "+ imageUri)
                        val imageStream = contentResolver.openInputStream(imageUri)
                        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ imageStream " + imageStream)
                        val selectedImage = BitmapFactory.decodeStream(imageStream)
                        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ selectedImage " + selectedImage)



                        val baos = ByteArrayOutputStream()
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 30, baos);

                        imageCapturedBitmap = selectedImage

                        capturedImageView.setImageBitmap(selectedImage)
                        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ imageCapturedBitmap " + imageCapturedBitmap)
                        //println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ capturedImageView.setImageBitmap(selectedImage) " + capturedImageView.setImageBitmap(selectedImage))

                        //capturedImageView.setImageBitmap(data.extras.get("data") as Bitmap)
                    } catch (e: FileNotFoundException) {
                        println("In catch of gallery!")

                        e.printStackTrace()
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                    }

                } else {
                    println("in else of gallery!")
                    Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show()
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
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

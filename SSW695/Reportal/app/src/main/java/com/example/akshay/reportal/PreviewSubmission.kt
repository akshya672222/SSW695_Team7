package com.example.akshay.reportal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_preview_submission.*
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*


class PreviewSubmission : AppCompatActivity() {

    //String [] nameCategories = {"Low", "Medium", "High"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_submission)
        buttonPost.setText("POST")
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val extra = intent.extras["image"] as Bitmap
        imageViewPreviewPostIssue.setImageBitmap(extra)

        buttonPost.setOnClickListener {

            val alertDialog = AlertDialog.Builder(this ).create()
            val title: String = textViewTitle.text.toString()
            val description: String = textViewDescription.text.toString()
            val categorySelected: String = category.selectedItem.toString ()
            val prioritySelected: String = priority.selectedItem.toString ()

            if(title.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please enter Title!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if(description.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please enter Description!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if(categorySelected.isEmpty()){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please select a category!")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else if(prioritySelected == ""){
                alertDialog.setTitle("Error!")
                alertDialog.setMessage("Please select a priority")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", {
                    dialogInterface, i -> Toast.makeText(applicationContext, "Please fill empty fields", Toast.LENGTH_LONG).show()
                })
                alertDialog.show()
            }
            else {
                Toast.makeText(this, "Post Submitted", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
            }

        }
    }

}

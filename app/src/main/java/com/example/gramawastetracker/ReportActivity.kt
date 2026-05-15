package com.example.gramawastetracker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import org.osmdroid.util.GeoPoint

class ReportActivity : AppCompatActivity() {

    private lateinit var imagePreview: ImageView

    private var imageUri: Uri? = null

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_report)

        val issueInput =
            findViewById<EditText>(R.id.issueInput)

        val locationInput =
            findViewById<EditText>(R.id.locationInput)

        val wasteSpinner =
            findViewById<Spinner>(R.id.wasteSpinner)

        val submitBtn =
            findViewById<Button>(R.id.submitBtn)

        val selectImageBtn =
            findViewById<Button>(R.id.selectImageBtn)

        imagePreview =
            findViewById(R.id.imagePreview)

        // ♻ Waste Types
        val wasteTypes = arrayOf(
            "Wet Waste",
            "Dry Waste",
            "Plastic Waste",
            "Overflow Bin",
            "Other"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            wasteTypes
        )

        wasteSpinner.adapter = adapter

        // 📷 IMAGE PICKER
        selectImageBtn.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_PICK)

            intent.type = "image/*"

            startActivityForResult(
                intent,
                IMAGE_REQUEST_CODE
            )
        }

        // ✅ SUBMIT REPORT
        submitBtn.setOnClickListener {

            val issue =
                issueInput.text.toString().trim()

            val location =
                locationInput.text.toString().trim()

            // VALIDATION
            if (
                issue.isEmpty() ||
                location.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // 🔥 FIREBASE DATABASE
                val db =
                    FirebaseDatabase
                        .getInstance()
                        .reference

                // 📦 DATA OBJECT
                val reportData = mapOf(
                    "issue" to issue,
                    "location" to location,
                    "wasteType" to wasteSpinner.selectedItem.toString()
                )

                // ☁ SAVE TO FIREBASE
                db.child("reports")
                    .push()
                    .setValue(reportData)
                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Complaint Submitted ✔",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnFailureListener {

                        Toast.makeText(
                            this,
                            "Firebase Failed ❌",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                // 🚨 ADD BLACKSPOT
                SharedData.blackspots.add(

                    GeoPoint(
                        12.9730 +
                                (0..10).random() * 0.0001,

                        77.5960 +
                                (0..10).random() * 0.0001
                    )
                )

                // 🧹 CLEAR INPUTS
                issueInput.text.clear()

                locationInput.text.clear()

                imagePreview.setImageDrawable(null)
            }
        }
    }

    // 🖼 IMAGE RESULT
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (
            requestCode == IMAGE_REQUEST_CODE &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {

            imageUri = data.data

            imagePreview.setImageURI(imageUri)
        }
    }
}

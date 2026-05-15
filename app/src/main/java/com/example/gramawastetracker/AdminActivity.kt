package com.example.gramawastetracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin)

        val complaintList = findViewById<ListView>(R.id.complaintList)

        // Demo complaints
        val complaints = mutableListOf(
            "🚨 Overflow Garbage - Market Area",
            "🗑 Plastic Waste - Bus Stop",
            "🚮 Wet Waste - School Road",
            "⚠ Garbage Blackspot - Temple Street"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            complaints
        )

        complaintList.adapter = adapter

        // Click complaint
        complaintList.setOnItemClickListener { _, _, position, _ ->

            val complaint = complaints[position]

            AlertDialog.Builder(this)
                .setTitle("Complaint Details")
                .setMessage(complaint)
                .setPositiveButton("Resolve") { _, _ ->

                    complaints.removeAt(position)
                    adapter.notifyDataSetChanged()

                    Toast.makeText(
                        this,
                        "Complaint Resolved ✔",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("Close", null)
                .show()
        }
    }
}
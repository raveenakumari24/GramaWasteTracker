package com.example.gramawastetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.mapBtn).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
//        findViewById<Button>(R.id.reportBtn).setOnClickListener {
//            startActivity(Intent(this, ReportActivity::class.java))
//        }
        val reportBtn = findViewById<Button>(R.id.reportBtn)

        reportBtn.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }




        val adminBtn = findViewById<Button>(R.id.adminBtn)

        adminBtn.setOnClickListener {
            startActivity(Intent(this, AdminMapActivity::class.java))
        }
    }
}
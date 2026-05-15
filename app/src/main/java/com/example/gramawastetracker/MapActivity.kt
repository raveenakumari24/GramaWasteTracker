package com.example.gramawastetracker

import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.random.Random


private var alertShown = false


class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var tractorMarker: Marker
    private lateinit var distanceText: TextView

    private val handler =
        Handler(Looper.getMainLooper())

    // 👤 USER LOCATION
    private val userLocation =
        GeoPoint(12.9732, 77.5962)

    // 🚜 START LOCATION
    private var tractorLocation =
        GeoPoint(12.9716, 77.5946)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        Configuration.getInstance().userAgentValue =
            packageName

        setContentView(R.layout.activity_map)

        map = findViewById(R.id.map)

        distanceText =
            findViewById(R.id.distanceText)

        map.setMultiTouchControls(true)

        map.controller.setZoom(18.0)

        map.controller.setCenter(tractorLocation)

        // 🚜 CREATE TRACTOR
        tractorMarker = Marker(map)

        tractorMarker.position = tractorLocation

        // REMOVE TITLE
        tractorMarker.title = null

        // REMOVE INFO WINDOW
        tractorMarker.closeInfoWindow()

        // USE BUILT-IN ICON
        tractorMarker.icon =
            ContextCompat.getDrawable(
                this,
                android.R.drawable.ic_menu_compass
            )

        tractorMarker.setAnchor(
            Marker.ANCHOR_CENTER,
            Marker.ANCHOR_CENTER
        )

        map.overlays.add(tractorMarker)

        startMovement()
    }

    private fun startMovement() {

        handler.postDelayed(object : Runnable {

            override fun run() {

                // SMOOTH MOVEMENT
                val latMove =
                    Random.nextDouble(
                        -0.00015,
                        0.00015
                    )

                val lngMove =
                    Random.nextDouble(
                        -0.00015,
                        0.00015
                    )

                tractorLocation =
                    GeoPoint(
                        tractorLocation.latitude + latMove,
                        tractorLocation.longitude + lngMove
                    )

                // MOVE TRACTOR
                tractorMarker.position =
                    tractorLocation

                // FOLLOW CAMERA
                map.controller.animateTo(
                    tractorLocation
                )

                // REFRESH MAP
                map.invalidate()

                // DISTANCE
                val distance =
                    calculateDistance(
                        userLocation,
                        tractorLocation
                    )

                distanceText.text =
                    "Vehicle Distance / ವಾಹನದ ದೂರ: ${distance.toInt()} m"

                // 🚨 ALERT WHEN VEHICLE IS NEAR

                if (distance <= 100 && !alertShown) {

                    alertShown = true

                    android.widget.Toast.makeText(
                        this@MapActivity,
                        "🚨 Waste Vehicle is near your house!",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                }

                if (distance > 100) {
                    alertShown = false
                }


                handler.postDelayed(this, 1000)
            }

        }, 1000)
    }

    private fun calculateDistance(
        p1: GeoPoint,
        p2: GeoPoint
    ): Float {

        val l1 = Location("A")

        l1.latitude = p1.latitude
        l1.longitude = p1.longitude

        val l2 = Location("B")

        l2.latitude = p2.latitude
        l2.longitude = p2.longitude

        return l1.distanceTo(l2)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}


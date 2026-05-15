package com.example.gramawastetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class AdminMapActivity : AppCompatActivity() {

    private lateinit var map: MapView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        Configuration.getInstance().userAgentValue =
            packageName

        setContentView(R.layout.activity_admin_map)

        map = findViewById(R.id.adminMap)

        map.setMultiTouchControls(true)

        val center =
            GeoPoint(12.9730, 77.5960)

        map.controller.setZoom(16.0)

        map.controller.setCenter(center)

        // 🚨 BLACKSPOTS
        val blackspots = listOf(

            GeoPoint(12.9720, 77.5950),

            GeoPoint(12.9735, 77.5965),

            GeoPoint(12.9745, 77.5975)
        )

        for (spot in blackspots) {

            val marker = Marker(map)

            marker.position = spot

            marker.title = "🚨"

            marker.setAnchor(
                Marker.ANCHOR_CENTER,
                Marker.ANCHOR_CENTER
            )

            marker.closeInfoWindow()

            marker.setOnMarkerClickListener { m, _ ->

                map.overlays.remove(m)

                map.invalidate()

                Toast.makeText(
                    this,
                    "✅ Cleaned Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                true
            }

            map.overlays.add(marker)
        }
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


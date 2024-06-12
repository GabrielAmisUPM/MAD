package es.upm.MAD_GA_MF.helloworldKt

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.BufferedReader

class OpenStreetMapActivity : AppCompatActivity() {
    private val TAG = "btaOpenStreetMapActivity"
    private lateinit var map: MapView
    private var isMarkerMode = false
    private lateinit var markerPopup: View
    private lateinit var markerName: EditText
    private lateinit var markerCoordinates: TextView
    private lateinit var saveButton: Button
    private lateinit var latestLocation: GeoPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_street_map)

        Log.d(TAG, "onCreate: The activity is being created.")

        val bundle = intent.getBundleExtra("locationBundle")
        val location: Location? = bundle?.getParcelable("location")

        val buttonQuit: Button = findViewById(R.id.QuitButton)
        val buttonMarker: Button = findViewById(R.id.MarkerButton)
        markerPopup = findViewById(R.id.markerPopup)
        val cancelButton: Button = findViewById(R.id.CancelButton)
        saveButton = findViewById(R.id.SaveButton)
        markerName = findViewById(R.id.markerName)
        markerCoordinates = findViewById(R.id.markerCoordinates)

        buttonQuit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonMarker.setOnClickListener {
            isMarkerMode = !isMarkerMode
            buttonMarker.text = if (isMarkerMode) "Marker is ON" else "Marker is OFF"
        }

        cancelButton.setOnClickListener {
            markerPopup.visibility = View.GONE
            isMarkerMode = false
            buttonMarker.text = "Marker is OFF"
        }

        saveButton.setOnClickListener {
            val name = markerName.text.toString()
            val point = latestLocation
            addMarker(point, name)
            saveCoordinatesToCSV(name, point)
            markerPopup.visibility = View.GONE
            isMarkerMode = false
            buttonMarker.text = "Marker is OFF"
        }

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(18.0)

        if (location != null) {
            Log.i(TAG, "onCreate: Location["+location.altitude+"]["+location.latitude+"]["+location.longitude+"][")

            Configuration.getInstance().load(applicationContext, getSharedPreferences("osm", MODE_PRIVATE))

            val startPoint = GeoPoint(location.latitude, location.longitude)
            map.controller.setCenter(startPoint)

            addMarker(startPoint, "My current location")
        }

        map.setOnTouchListener { v, event ->
            if (isMarkerMode && event.action == MotionEvent.ACTION_DOWN) {
                val geoPoint = map.projection.fromPixels(event.x.toInt(), event.y.toInt()) as GeoPoint
                latestLocation = geoPoint
                markerCoordinates.text = "Lat: ${geoPoint.latitude}, Lon: ${geoPoint.longitude}"
                markerPopup.visibility = View.VISIBLE
            }
            false
        }

        // Load markers from CSV file
        loadMarkersFromCSV()
    }

    private fun addMarker(point: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title
        map.overlays.add(marker)
        map.invalidate() // Reload map
    }

    private fun saveCoordinatesToCSV(name: String, point: GeoPoint) {
        val fileName = "gps_coordinates.csv"
        val file = File(getExternalFilesDir(null), fileName)
        val fileOutputStream = FileOutputStream(file, true)
        fileOutputStream.write("$name,${point.latitude},${point.longitude}\n".toByteArray())
        fileOutputStream.close()
    }

    private fun loadMarkersFromCSV() {
        val fileName = "gps_coordinates.csv"
        val file = File(getExternalFilesDir(null), fileName)
        if (!file.exists()) return

        val fileInputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var line: String?

        while (bufferedReader.readLine().also { line = it } != null) {
            val parts = line!!.split(",")
            if (parts.size == 3) {
                val name = parts[0]
                val latitude = parts[1].toDoubleOrNull()
                val longitude = parts[2].toDoubleOrNull()
                if (latitude != null && longitude != null) {
                    val point = GeoPoint(latitude, longitude)
                    addMarker(point, name)
                }
            }
        }

        bufferedReader.close()
        inputStreamReader.close()
        fileInputStream.close()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }
}

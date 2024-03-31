package es.upm.MAD_GA_MF.helloworldKt

import android.content.ContentValues
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ThirdActivity : AppCompatActivity() {
    private val TAG = "btaThirdActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        Log.d(ContentValues.TAG, "onCreate: The activity is being created.");

        val bundle = intent.getBundleExtra("locationBundle")
        val location: Location? = bundle?.getParcelable("location")

        if (location != null) {
            Log.i(ContentValues.TAG, "onCreate: Location["+location.altitude+"]["+location.latitude+"]["+location.longitude+"][")
        };/*
        // Add item to Firebase realtime database
        val addReportButton: Button = findViewById(R.id.addReportButton)
        val editTextReport: EditText = findViewById(R.id.editTextReport)
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        addReportButton.setOnClickListener {
            val reportText = editTextReport.text.toString().trim()
            if (reportText.isNotEmpty() && userId != null) {
                val report = mapOf(
                    "userId" to userId,
                    "timestamp" to timestamp,
                    "report" to reportText,
                    "latitude" to latitude,
                    "longitude" to longitude
                )
                addReportToDatabase(report)
            } else {
                Toast.makeText(this, "Report name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")
        Log.d(TAG, "Latitude: $latitude, Longitude: $longitude")
        val buttonPrevious: Button = findViewById(R.id.ThirdButton)
        buttonPrevious.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
    private fun addReportToDatabase(report: Map<String, Any>) {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("hotspots").push()
        databaseReference.setValue(report)
            .addOnSuccessListener {
                Toast.makeText(this, "Report added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add report: ${e.message}", Toast.LENGTH_SHORT).show()
            }*/
    }
}
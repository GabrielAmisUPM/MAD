package es.upm.MAD_GA_MF.helloworldKt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ThirdActivity : AppCompatActivity() {
    class ThirdActivity : AppCompatActivity() {
        private val TAG = "btaThirdActivity"
        override fun onCreate(savedInstanceState: Bundle?) {
            println("DEBUG")
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_third)
            val latitude = intent.getStringExtra("latitude")
            val longitude = intent.getStringExtra("longitude")
            Log.d(TAG, "Latitude: $latitude, Longitude: $longitude")
            val buttonPrevious: Button = findViewById(R.id.ThirdButton)
            buttonPrevious.setOnClickListener {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
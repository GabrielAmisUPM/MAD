package es.upm.MAD_GA_MF.helloworldKt

import android.content.ContentValues
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        Log.d(ContentValues.TAG, "onCreate: The activity is being created.");

        val bundle = intent.getBundleExtra("locationBundle")
        val location: Location? = bundle?.getParcelable("location")

        if (location != null) {
            Log.i(ContentValues.TAG, "onCreate: Location["+location.altitude+"]["+location.latitude+"]["+location.longitude+"][")
        };


        val buttonPrevious: Button = findViewById(R.id.ThirdButton)
        buttonPrevious.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
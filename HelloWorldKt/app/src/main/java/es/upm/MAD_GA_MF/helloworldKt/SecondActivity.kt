package es.upm.MAD_GA_MF.helloworldKt

import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    private val TAG = "btaSecondActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.d(TAG, "onCreate: The activity is being created.");

        val bundle = intent.getBundleExtra("locationBundle")
        val location: Location? = bundle?.getParcelable("location")

        if (location != null) {
            Log.i(TAG, "onCreate: Location["+location.altitude+"]["+location.latitude+"]["+location.longitude+"][")
        };

        val buttonNext: Button = findViewById(R.id.SecondButton)
        buttonNext.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        val buttonPrevious: Button = findViewById(R.id.ReturnButton)
        buttonPrevious.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
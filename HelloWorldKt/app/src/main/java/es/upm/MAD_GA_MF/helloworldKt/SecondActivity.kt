package es.upm.MAD_GA_MF.helloworldKt

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


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

        val buttonPrevious: Button = findViewById(R.id.secondPreviousButton)
        buttonPrevious.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val listView: ListView = findViewById(R.id.lvCoordinates)
        val headerView = layoutInflater.inflate(R.layout.listview_header, listView, false)
        listView.addHeaderView(headerView, null, false)

        val adapter = CoordinatesAdapter(this, readFileContents())
        listView.adapter = adapter

    }

    private class CoordinatesAdapter(context: Context, private val coordinatesList: List<List<String>>) :
        ArrayAdapter<List<String>>(context, R.layout.listview_item, coordinatesList) {
        private val inflater: LayoutInflater = LayoutInflater.from(context)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: inflater.inflate(R.layout.listview_item, parent, false)
            val timestampTextView: TextView = view.findViewById(R.id.tvTimestamp)
            val latitudeTextView: TextView = view.findViewById(R.id.tvLatitude)
            val longitudeTextView: TextView = view.findViewById(R.id.tvLongitude)
            val item = coordinatesList[position]
            timestampTextView.text = formatTimestamp(item[0].toLong())
            latitudeTextView.text = formatCoordinate(item[1].toDouble())
            longitudeTextView.text = formatCoordinate(item[2].toDouble())
            view.setOnClickListener {
                val intent = Intent(context, ThirdActivity::class.java).apply {
                    putExtra("latitude", item[1])
                    putExtra("longitude", item[2])
                }

                context.startActivity(intent)
            }
            return view
        }
        private fun formatTimestamp(timestamp: Long): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return formatter.format(Date(timestamp))
        }
        private fun formatCoordinate(value: Double): String {
            return String.format("%.6f", value)
        }
    }
    private fun readFileContents(): List<List<String>> {
        val fileName = "gps_coordinates.csv"
        return try {
            openFileInput(fileName).bufferedReader().useLines { lines ->
                lines.map { it.split(";").map(String::trim) }.toList()
            }
        } catch (e: IOException) {
            listOf(listOf("Error reading file: ${e.message}"))
        }
    }
}
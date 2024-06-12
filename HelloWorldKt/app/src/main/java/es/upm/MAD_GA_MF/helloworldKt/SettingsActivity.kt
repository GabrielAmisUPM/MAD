package es.upm.MAD_GA_MF.helloworldKt


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Get the current user
        val currentUser = auth.currentUser

        // Set the user's name in the TextView
        val userNameTextView: TextView = findViewById(R.id.tvUserName)
        if (currentUser != null) {
            userNameTextView.text = "Welcome, ${currentUser.displayName}"
        } else {
            userNameTextView.text = "Welcome, User"
        }

        // BottomNavigationMenu
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_map -> {
                    val intent = Intent(this, OpenStreetMapActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_list -> {
                    val intent = Intent(this, SecondActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        val listView: ListView = findViewById(R.id.lvPreferences)
        val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val allEntries = sharedPreferences.all

        val listItems = ArrayList<String>()
        for ((key, value) in allEntries) {
            listItems.add("$key: $value")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listView.adapter = adapter
    }
}

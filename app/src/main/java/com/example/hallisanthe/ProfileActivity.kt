package com.example.hallisanthe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.Toast
class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)
        val uploadsBtn =
            findViewById<Button>(R.id.myUploadsBtn)

        val settingsBtn =
            findViewById<Button>(R.id.settingsBtn)

        val helpBtn =
            findViewById<Button>(R.id.helpBtn)

        val logoutBtn =
            findViewById<Button>(R.id.logoutBtn)

        uploadsBtn.setOnClickListener {

            startActivity(
                Intent(this, AddProductActivity::class.java)
            )
        }

        settingsBtn.setOnClickListener {

            Toast.makeText(
                this,
                "Settings Coming Soon",
                Toast.LENGTH_SHORT
            ).show()
        }

        helpBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW)

            intent.data =
                Uri.parse("https://wa.me/919876543210")

            startActivity(intent)
        }

        logoutBtn.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }
    }
}
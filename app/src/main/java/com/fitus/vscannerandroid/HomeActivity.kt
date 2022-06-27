package com.fitus.vscannerandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fitus.vscannerandroid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.openActivityCamera.setOnClickListener {
            Toast.makeText(this, "Home to Cam",Toast.LENGTH_LONG).show();
            val intent = Intent(this@HomeActivity, CameraActivity::class.java)
            startActivity(intent)
        }
        binding.openActivityHistory.setOnClickListener {
            val intent = Intent(this@HomeActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
        binding.openActivityProfile.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }


}
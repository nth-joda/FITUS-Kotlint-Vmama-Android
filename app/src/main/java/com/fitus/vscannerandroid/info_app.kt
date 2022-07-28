package com.fitus.vscannerandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitus.vscannerandroid.databinding.ActivityInfoAppBinding

class info_app : AppCompatActivity() {
    private lateinit var binding: ActivityInfoAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToProfile.setOnClickListener {
            val intent = Intent(this@info_app, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
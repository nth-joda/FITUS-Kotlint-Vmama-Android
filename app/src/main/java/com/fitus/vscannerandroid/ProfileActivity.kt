package com.fitus.vscannerandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.fitus.vscannerandroid.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openActivityChangePassword.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChangePassword::class.java)
            startActivity(intent)
        }
    }
}
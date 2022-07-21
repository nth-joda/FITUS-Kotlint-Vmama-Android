package com.fitus.vscannerandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitus.vscannerandroid.databinding.ActivityHomeBinding
import com.fitus.vscannerandroid.databinding.ActivityResetPasswordBinding

class ResetPassword : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAccept.setOnClickListener {
            val intent = Intent(this@ResetPassword, ResetPasswordSuccessful::class.java)
            startActivity(intent)
        }
    }
}
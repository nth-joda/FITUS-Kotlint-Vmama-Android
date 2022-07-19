package com.fitus.vscannerandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fitus.vscannerandroid.databinding.ActivityForgotPasswordBinding


class ForgotPassword : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAcceptForgotPassword.setOnClickListener {
            val intent = Intent(this@ForgotPassword, ChangePasswordOtp::class.java)
            startActivity(intent)
        }
    }

}
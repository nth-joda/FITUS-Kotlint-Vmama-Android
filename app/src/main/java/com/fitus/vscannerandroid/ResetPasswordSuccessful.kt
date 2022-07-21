package com.fitus.vscannerandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitus.vscannerandroid.databinding.ActivityResetPasswordBinding
import com.fitus.vscannerandroid.databinding.ActivityResetPasswordSuccessfulBinding
import com.fitus.vscannerandroid.ui.login.LoginActivity

class ResetPasswordSuccessful : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordSuccessfulBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordSuccessfulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@ResetPasswordSuccessful, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
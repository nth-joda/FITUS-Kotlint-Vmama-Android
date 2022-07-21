package com.fitus.vscannerandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fitus.vscannerandroid.databinding.ActivityChangePasswordBinding
import com.fitus.vscannerandroid.databinding.ActivityResetPasswordBinding

class ChangePassword : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAccept.setOnClickListener {
            val intent = Intent(this@ChangePassword, ResetPassword::class.java)
            startActivity(intent)
        }
    }
}
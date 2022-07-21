package com.fitus.vscannerandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.fitus.vscannerandroid.databinding.ActivityChangePassWordOtpBinding


class ChangePasswordOtp : AppCompatActivity() {
    private lateinit var binding: ActivityChangePassWordOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassWordOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAccept.setOnClickListener {
            val intent = Intent(this@ChangePasswordOtp, ResetPassword::class.java)
            startActivity(intent)
        }

setupOTPinputs();
    }
    private fun setupOTPinputs() {

        binding.inputCode1.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(this@ChangePasswordOtp,s,Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.inputCode2.requestFocus();
                }
            }
        })

        binding.inputCode2.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(this@ChangePasswordOtp,s,Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.inputCode3.requestFocus();
                }
                else{
                    binding.inputCode1.setText(null);
                    binding.inputCode1.requestFocus();
                }
            }
        })

        binding.inputCode3.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(this@ChangePasswordOtp,s,Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.inputCode4.requestFocus();
                }
                else{
                    binding.inputCode2.setText(null);
                    binding.inputCode2.requestFocus();
                }
            }
        })

        binding.inputCode4.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(this@ChangePasswordOtp,s,Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.inputCode5.requestFocus();
                }
                else{
                    binding.inputCode3.setText(null);
                    binding.inputCode3.requestFocus();
                }
            }
        })

        binding.inputCode5.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
//                Toast.makeText(this@ChangePasswordOtp,s,Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Toast.makeText(this@ChangePasswordOtp,s,Toast.LENGTH_SHORT).show()

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.inputCode6.requestFocus();
                }
                else{
                    binding.inputCode4.setText(null);
                    binding.inputCode4.requestFocus();
                }

            }
        })

        binding.inputCode6.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(this@ChangePasswordOtp,"xac nhan di",Toast.LENGTH_SHORT).show()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    Toast.makeText(this@ChangePasswordOtp,"xac nhan di",Toast.LENGTH_SHORT).show()
                }
                else{
                    binding.inputCode6.requestFocus();
                }

            }
        })
    }

}
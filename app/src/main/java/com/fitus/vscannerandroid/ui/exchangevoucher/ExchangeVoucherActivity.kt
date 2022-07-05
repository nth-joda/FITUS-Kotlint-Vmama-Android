package com.fitus.vscannerandroid.ui.exchangevoucher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.fitus.vscannerandroid.databinding.ActivityExchangeVoucherBinding
import com.fitus.vscannerandroid.ui.scanreceipt.ScanReceiptViewModel
import com.fitus.vscannerandroid.ui.scanreceipt.ScanReceiptViewModelFactory
import com.fitus.vscannerandroid.ui.setVisibility

class ExchangeVoucherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeVoucherBinding

    private lateinit var viewModel: ExchangeVoucherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeVoucherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ExchangeVoucherViewModelFactory(application))
            .get(ExchangeVoucherViewModel::class.java)
    }

    fun loading(isLoading: Boolean) {
        binding.progressBar.setVisibility(isLoading)
    }
}
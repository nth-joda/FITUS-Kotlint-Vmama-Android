package com.fitus.vscannerandroid.ui.exchangevoucher

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitus.vscannerandroid.bussiness.scanreceipt.ExtractReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.OCRUseCase
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessExtractedReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessImageUC

class ExchangeVoucherViewModelFactory(val application: Application):
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeVoucherViewModel::class.java)) {
            return ExchangeVoucherViewModel(
                application,
                processImageUC = ProcessImageUC(),
                ocrUC = OCRUseCase(application),
                extractReceiptUC = ExtractReceiptUC(),
                processExtractedReceiptUC = ProcessExtractedReceiptUC(),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

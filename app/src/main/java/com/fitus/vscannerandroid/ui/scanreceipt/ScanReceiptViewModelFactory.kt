package com.fitus.vscannerandroid.ui.scanreceipt

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitus.vscannerandroid.bussiness.scanreceipt.ExtractReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.OCRUseCase
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessImageUC
import com.fitus.vscannerandroid.data.LoginDataSource
import com.fitus.vscannerandroid.data.LoginRepository
import com.fitus.vscannerandroid.ui.login.LoginViewModel

class ScanReceiptViewModelFactory(val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanReceiptViewModel::class.java)) {
            return ScanReceiptViewModel(
                application,
                processImageUC = ProcessImageUC(),
                ocrUC = OCRUseCase(application),
                extractReceiptUC = ExtractReceiptUC()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
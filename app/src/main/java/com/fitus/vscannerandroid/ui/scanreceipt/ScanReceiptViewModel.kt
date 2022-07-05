package com.fitus.vscannerandroid.ui.scanreceipt

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.fitus.vscannerandroid.bussiness.scanreceipt.ExtractReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.OCRUseCase
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessExtractedReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessImageUC
import com.fitus.vscannerandroid.data.model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScanReceiptViewModel(
    app: Application,
    private val processImageUC: ProcessImageUC,
    private val ocrUC: OCRUseCase,
    private val extractReceiptUC: ExtractReceiptUC,
    private val processExtractedReceiptUC: ProcessExtractedReceiptUC,
) : AndroidViewModel(app) {

    //reactive data structure to notify background process result
    private val _receipt = MutableStateFlow(Receipt())
    val receipt = _receipt.asStateFlow()

    fun scanReceipt(bitmap: Bitmap) {
        //Process add background
        viewModelScope.launch(Dispatchers.Default) {
            val processedBitmap = processImageUC(bitmap)
            val visionText = ocrUC(processedBitmap)
            //notify result when process done
            val extracted = extractReceiptUC(visionText)
            _receipt.value = processExtractedReceiptUC(extracted)
        }
    }

}
package com.fitus.vscannerandroid.ui.exchangevoucher

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitus.vscannerandroid.bussiness.scanreceipt.ExtractReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.OCRUseCase
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessExtractedReceiptUC
import com.fitus.vscannerandroid.bussiness.scanreceipt.ProcessImageUC
import com.fitus.vscannerandroid.data.model.Receipt
import com.google.mlkit.vision.text.Text
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeVoucherViewModel
constructor(
    app: Application,
    private val processImageUC: ProcessImageUC,
    private val ocrUC: OCRUseCase,
    private val extractReceiptUC: ExtractReceiptUC,
    private val processExtractedReceiptUC: ProcessExtractedReceiptUC,
) : AndroidViewModel(app) {

    var croppedImage: Bitmap? = null
    var image: Bitmap? = null

    //reactive data structure to notify background process result
    private val _receipt = MutableStateFlow(ExchangeVoucherState())
    val receipt = _receipt.asStateFlow()

    fun scanReceipt(bitmap: Bitmap) {
        //Process add background
        viewModelScope.launch(Dispatchers.Default) {
            _receipt.value = ExchangeVoucherState(isLoading = true)

            val processedBitmap = processImageUC(bitmap)
            val visionText = ocrUC(processedBitmap)
            //notify result when process done
            val extracted = extractReceiptUC(visionText)
            
            _receipt.value = ExchangeVoucherState(data = processExtractedReceiptUC(extracted))
        }
    }
}

data class ExchangeVoucherState(
    var isLoading: Boolean = false,
    var data: Receipt? = null
)
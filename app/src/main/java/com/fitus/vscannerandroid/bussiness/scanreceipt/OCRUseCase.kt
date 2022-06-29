package com.fitus.vscannerandroid.bussiness.scanreceipt

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OCRUseCase(val context: Context) {

    suspend operator fun invoke(imageUri: Uri): Text {
        return suspendCoroutine { continuation ->
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val inputImage = InputImage.fromFilePath(context, imageUri)
            recognizer.process(inputImage).addOnSuccessListener {
                continuation.resume(it)
            }
        }
    }


    suspend operator fun invoke(bitmap: Bitmap): Text {
        return suspendCoroutine { continuation ->
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            recognizer.process(inputImage).addOnSuccessListener {
                continuation.resume(it)
            }
        }
    }
}
package com.fitus.vscannerandroid.ui

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageProxy
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fitus.vscannerandroid.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.ByteBuffer

fun AppCompatActivity.repeatLifecycleFlow(flowCollect: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, flowCollect)
    }
}

fun Fragment.repeatLifecycleFlow(flowCollect: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, flowCollect)
    }
}

fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}


fun Activity.getOutputDir(): File {
    val mediaDir = externalMediaDirs.firstOrNull()?.let { file ->
        return@let File(file, resources.getString(R.string.app_name)).apply {
            mkdirs()
        }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
}

fun imageProxyToBitmap(image: ImageProxy): Bitmap {
    val planeProxy = image.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)

    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return Bitmap.createBitmap(
        bitmap,
        0,
        0,
        bitmap.width,
        bitmap.height,
        Matrix().apply { postRotate(image.imageInfo.rotationDegrees.toFloat()) },
        true
    )
}
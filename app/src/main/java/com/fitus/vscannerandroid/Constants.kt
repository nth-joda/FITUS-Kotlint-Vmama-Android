package com.fitus.vscannerandroid

import android.Manifest


object Constants {
    const val TAG = "cameraX"
    const val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSIONS = 123
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    const val INTENT_EXTRA_IMAGE_URI = "intent_extra_image_uri"
}
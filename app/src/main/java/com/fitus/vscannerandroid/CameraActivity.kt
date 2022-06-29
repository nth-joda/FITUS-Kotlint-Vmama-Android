package com.fitus.vscannerandroid

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fitus.vscannerandroid.databinding.ActivityCameraBinding
import com.fitus.vscannerandroid.ui.scanreceipt.ScanReceiptActivity
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity() {

    //    Binding interacting
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProvider: ProcessCameraProvider

    // Crop img:
    private val cropActivityResultContracts = object : ActivityResultContract<Any?, Uri>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity().setAspectRatio(16, 9).getIntent(this@CameraActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        //Set event to buttons
        binding.imageCapture.setOnClickListener() {
            takePhoto()
        }

        binding.backHome.setOnClickListener() {
            val intent = Intent(this@CameraActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                Constants.FILE_NAME_FORMAT,
                Locale.getDefault()
            )
                .format(
                    System
                        .currentTimeMillis()
                ) + ".jpg"
        )
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo saved"
                    CropImage.activity(savedUri).start(this@CameraActivity);
                    Toast.makeText(this@CameraActivity, "${msg} ${savedUri}", Toast.LENGTH_LONG)
                        .show()
//                    cropActivityResultLauncher = registerForActivityResult(cropActivityResultContracts){
//                        it?.let{ uri ->
//                            Log.d("URI", uri.toString())
//                        }
//                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(
                        Constants.TAG,
                        "onError: ${exception.message}",
                        exception
                    )
                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                val scanReceiptIntent = Intent(applicationContext, ScanReceiptActivity::class.java)
                scanReceiptIntent.putExtra(Constants.INTENT_EXTRA_IMAGE_URI, resultUri)
                startActivity(scanReceiptIntent)

                Toast.makeText(
                    this@CameraActivity,
                    "Croped Img: " + resultUri.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val extensionsManagerFuture =
                ExtensionsManager.getInstanceAsync(applicationContext, cameraProvider)
            extensionsManagerFuture.addListener({
                val extensionsManager = extensionsManagerFuture.get()
                val preview = Preview.Builder().build()
                    .also { mPreview -> mPreview.setSurfaceProvider(binding.viewFinder.surfaceProvider) }
                imageCapture = ImageCapture.Builder().build()
                var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                cameraSelector = setCameraMode(extensionsManager, cameraSelector, ExtensionMode.HDR)
                cameraSelector =
                    setCameraMode(extensionsManager, cameraSelector, ExtensionMode.NIGHT)
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                } catch (e: Exception) {
                    Log.d(Constants.TAG, "StartCamera Fail: ", e)
                }
            }, ContextCompat.getMainExecutor(this))


        }, ContextCompat.getMainExecutor(this))

    }

    private fun setCameraMode(
        extensionsManager: ExtensionsManager,
        cameraSelector: CameraSelector,
        mode: Int
    ): CameraSelector {
        if (extensionsManager.isExtensionAvailable(
                cameraSelector,
                mode
            )
        ) {
            cameraProvider.unbindAll()
            // Retrieve extension enabled camera selector
            return extensionsManager.getExtensionEnabledCameraSelector(
                cameraSelector,
                mode
            )
        }
        return cameraSelector
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            if (allPermissionGranted()) {
                // our code
                startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
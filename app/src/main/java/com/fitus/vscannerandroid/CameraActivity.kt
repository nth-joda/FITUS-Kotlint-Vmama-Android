package com.fitus.vscannerandroid

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fitus.vscannerandroid.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity() {

//    Binding interacting
    private lateinit var binding: ActivityCameraBinding
    private var imageCapture:ImageCapture?=null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        outputDirectory = getOutputDirectory()
        cameraExecutor= Executors.newSingleThreadExecutor()

        if(allPermissionGranted()){
            startCamera()
        }
        else{
            ActivityCompat.requestPermissions(this, Constants.REQUIRED_PERMISTIONS, Constants.REQUEST_CODE_PERMISSIONS)
        }

        //Set event to buttons
        binding.imageCapture.setOnClickListener(){
            takePhoto()
        }

        binding.backHome.setOnClickListener(){
            val intent = Intent(this@CameraActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    private fun getOutputDirectory(): File{
        val mediaDir = externalMediaDirs.firstOrNull()?.let{ mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if(mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private fun takePhoto(){
        val imageCapture= imageCapture?: return
        val photoFile= File(outputDirectory,
            SimpleDateFormat(Constants.FILE_NAME_FORMAT,
                Locale.getDefault())
                .format(System
                    .currentTimeMillis()) + ".jpg")
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(this),
            object :ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                    val savedUri= Uri.fromFile(photoFile)
                    val msg = "Photo saved"
                    Toast.makeText(this@CameraActivity, "${msg} ${savedUri}", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(Constants.TAG,
                        "onError: ${exception.message}",
                        exception)
                }

            })
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider:ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also { mPreview-> mPreview.setSurfaceProvider(binding.viewFinder.surfaceProvider)  }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try{
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e:Exception){
                Log.d(Constants.TAG, "StartCamera Fail: ",e)
            }
        }, ContextCompat.getMainExecutor(this))

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.REQUEST_CODE_PERMISSIONS){
            if(allPermissionGranted()){
                // our code
                startCamera()
            }
            else{
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionGranted() =
        Constants.REQUIRED_PERMISTIONS.all { ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
package com.fitus.vscannerandroid.ui.scanreceipt

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.fitus.vscannerandroid.Constants
import com.fitus.vscannerandroid.databinding.ActivityScanReceiptBinding
import com.fitus.vscannerandroid.ui.repeatLifecycleFlow
import kotlinx.coroutines.flow.collectLatest

class ScanReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanReceiptBinding

    private lateinit var viewModel: ScanReceiptViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.extras?.get(Constants.INTENT_EXTRA_IMAGE_URI) as Uri
        val bitmap = convertUriToBitmap(imageUri)

        binding.scanningImgPreview.setImageURI(imageUri)
        repeatLifecycleFlow {
            //notify receipt result here
            viewModel.receipt.collectLatest { receipt ->
                //TODO do something with the result


                binding.tvReceipt.text = receipt.text
                var priceList:String = "Giá: \n";
                for(price in receipt.prices!!){
                    priceList = priceList + price +"\n"
                }
                binding.tvReceipt.text = priceList
//                binding.tvReceipt.text = binding.tvReceipt.text as String +"\n Giá nè: \n"
//                for( price in receipt.prices!!){
//                    binding.tvReceipt.text = binding.tvReceipt.text as String + price + "\n"
//                }


                if(!binding.tvReceipt.text.equals("")){
                    binding.progressBar.isVisible = false;
                    Toast.makeText(this@ScanReceiptActivity, "Scan done", Toast.LENGTH_SHORT).show()
                }


            }
        }

        viewModel = ViewModelProvider(this, ScanReceiptViewModelFactory(application))
            .get(ScanReceiptViewModel::class.java)

        viewModel.scanReceipt(bitmap)
    }

    private fun convertUriToBitmap(imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        } else {
            val source: ImageDecoder.Source =
                ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }
    }
}
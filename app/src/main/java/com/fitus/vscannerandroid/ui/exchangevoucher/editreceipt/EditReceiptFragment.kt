package com.fitus.vscannerandroid.ui.exchangevoucher.editreceipt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.fitus.vscannerandroid.R
import com.fitus.vscannerandroid.databinding.FragmentEditReceiptBinding
import com.fitus.vscannerandroid.ui.exchangevoucher.ExchangeVoucherActivity
import com.fitus.vscannerandroid.ui.exchangevoucher.ExchangeVoucherViewModel
import com.fitus.vscannerandroid.ui.exchangevoucher.ExchangeVoucherViewModelFactory
import com.fitus.vscannerandroid.ui.repeatLifecycleFlow
import kotlinx.coroutines.flow.collectLatest


class EditReceiptFragment : Fragment(R.layout.fragment_edit_receipt) {
    private var _binding: FragmentEditReceiptBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExchangeVoucherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditReceiptBinding.bind(view)

        viewModel = ViewModelProvider(activity!!)[ExchangeVoucherViewModel::class.java]

        binding.scanningImgPreview.setImageBitmap(viewModel.croppedImage)

        viewModel.scanReceipt(viewModel.croppedImage!!)

        repeatLifecycleFlow {

            //notify receipt result here
            viewModel.receipt.collectLatest { dataState ->
                //TODO do something with the result

                dataState.isLoading.let {
                    (requireActivity() as ExchangeVoucherActivity).loading(it)
                }

                dataState.data?.let { receipt ->
                    binding.tvReceipt.text = receipt.text
                    var priceList: String = "Giá: \n";
                    receipt.prices?.forEach { item -> priceList = priceList + item + "\n" }
                    priceList = priceList + "Sản phẩm\n"
                    receipt.products?.forEach { item -> priceList = priceList + item + "\n" }
                    binding.tvReceipt.text = priceList
                }
//                for(price in receipt.prices!!){
//                    priceList = priceList + price +"\n"
//                }
//                binding.tvReceipt.text = priceList
//                binding.tvReceipt.text = binding.tvReceipt.text as String +"\n Giá nè: \n"
//                for( price in receipt.prices!!){
//                    binding.tvReceipt.text = binding.tvReceipt.text as String + price + "\n"
//                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
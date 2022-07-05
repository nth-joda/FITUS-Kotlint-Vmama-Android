package com.fitus.vscannerandroid.ui.exchangevoucher.cropimage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fitus.vscannerandroid.R
import com.fitus.vscannerandroid.databinding.FragmentCropImageBinding
import com.fitus.vscannerandroid.ui.exchangevoucher.ExchangeVoucherViewModel
import com.fitus.vscannerandroid.ui.exchangevoucher.ExchangeVoucherViewModelFactory


class CropImageFragment : Fragment(R.layout.fragment_crop_image) {
    private var _binding: FragmentCropImageBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ExchangeVoucherViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCropImageBinding.bind(view)

        viewModel = ViewModelProvider(activity!!)[ExchangeVoucherViewModel::class.java]

        binding.civReceipt.setImageBitmap(viewModel.image)

        binding.apply {
            btnDone.setOnClickListener {
                viewModel.croppedImage = civReceipt.croppedImage
                findNavController().navigate(R.id.action_cropImageFragment_to_editReceiptFragment)
            }

            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
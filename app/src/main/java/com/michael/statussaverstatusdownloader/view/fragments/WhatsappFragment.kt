package com.michael.statussaverstatusdownloader.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.FragmentWhatsappBinding


class WhatsappFragment : Fragment() {

    private lateinit var binding: FragmentWhatsappBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            arguments?.let {
//                val repository = StatusRepository(requireActivity())
//                viewModel = ViewModelProvider(
//                    requireActivity(), StatusViewModelFactory(repository)
//                )[StatusViewModel::class.java]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_whatsapp, container, false)
    }

}
package com.michael.statussaverstatusdownloader.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.FragmentWBusinessBinding
import com.michael.statussaverstatusdownloader.databinding.FragmentWhatsappBinding
import com.michael.statussaverstatusdownloader.model.repository.WBusinessStatusRepository
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.view.adapters.MediaViewPagerAdapter
import com.michael.statussaverstatusdownloader.viewmodel.WBusinessStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.WhatsappStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.factories.WBusinessStatusViewModelFactory
import com.michael.statussaverstatusdownloader.viewmodel.factories.WhatsappStatusViewModelFactory


class WBusinessFragment : Fragment() {

    private var _binding: FragmentWBusinessBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: WBusinessStatusViewModel
    private val viewPagerTitles = arrayListOf("Images", "Videos", "Audios")
    val TAG = "WhatsappFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val repository = WBusinessStatusRepository(requireActivity())
            viewModel = ViewModelProvider(
                requireActivity(), WBusinessStatusViewModelFactory(repository)
            )[WBusinessStatusViewModel::class.java]

            // Set up swipe-to-refresh color
            swipeRefreshLayout.setColorSchemeResources(R.color.green_light)
            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.background)
            // Observe data loading state
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                swipeRefreshLayout.isRefreshing = isLoading
            }

            // Set up ViewPager and TabLayout
            val viewPagerAdapter = MediaViewPagerAdapter(
                fragmentActivity = requireActivity(),
                imagesType = Constants.MEDIA_TYPE_W_BUSINESS_STATUS_IMAGES,
                videosType = Constants.MEDIA_TYPE_W_BUSINESS_STATUS_VIDEOS,
                audiosType = Constants.MEDIA_TYPE_W_BUSINESS_STATUS_AUDIOS
            )
            statusViewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, statusViewPager) { tab, position ->
                tab.text = viewPagerTitles[position]
            }.attach()

            // Set up swipe-to-refresh
            binding.swipeRefreshLayout.setOnRefreshListener {
                refreshWBusinessStatus()
            }
        }
    }

    fun refreshWBusinessStatus() {
        Toast.makeText(activity, "Refreshing Whatsapp Business Status", Toast.LENGTH_SHORT).show()
        viewModel.getWBusinessStatus() // Pass viewLifecycleOwner here as well

    }

}
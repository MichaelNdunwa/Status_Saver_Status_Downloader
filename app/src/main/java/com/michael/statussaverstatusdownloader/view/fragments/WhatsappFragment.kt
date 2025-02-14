package com.michael.statussaverstatusdownloader.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.michael.statussaverstatusdownloader.databinding.FragmentWhatsappBinding
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.view.adapters.MediaViewPagerAdapter
import com.michael.statussaverstatusdownloader.viewmodel.WhatsappStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.factories.WhatsappStatusViewModelFactory
import com.michael.statussaverstatusdownloader.R


class WhatsappFragment : Fragment() {

    private var _binding: FragmentWhatsappBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: WhatsappStatusViewModel
    private val viewPagerTitles = arrayListOf("Images", "Videos", "Audios")
    val TAG = "WhatsappFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWhatsappBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val repository = WhatsappStatusRepository(requireActivity())
            viewModel = ViewModelProvider(
                requireActivity(), WhatsappStatusViewModelFactory(repository)
            )[WhatsappStatusViewModel::class.java]

//            viewModel.getWhatsAppStatus(viewLifecycleOwner) // Pass viewLifecycleOwner here
            // Set up swipe-to-refresh color
            swipeRefreshLayout.setColorSchemeResources(R.color.green_light)
            swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.background)

            // Calculate padding to center the spinner
//            val displayMetrics = resources.displayMetrics
//            val screenHeight = displayMetrics.heightPixels
//            val actionBarHeight = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics
//            ).toInt()
//            val spinnerHeight = 100
//            val paddingTop = (screenHeight / 2 ) - (actionBarHeight + spinnerHeight)
//            swipeRefreshLayout.setProgressViewOffset(false, 0, paddingTop)

            // Observe data loading state
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                swipeRefreshLayout.isRefreshing = isLoading
//                initialLoadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            // Set up ViewPager and TabLayout
            val viewPagerAdapter = MediaViewPagerAdapter(
                fragmentActivity = requireActivity(),
                imagesType = Constants.MEDIA_TYPE_WHATSAPP_STATUS_IMAGES,
                videosType = Constants.MEDIA_TYPE_WHATSAPP_STATUS_VIDEOS,
                audiosType = Constants.MEDIA_TYPE_WHATSAPP_STATUS_AUDIOS
            )
            statusViewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, statusViewPager) { tab, position ->
                tab.text = viewPagerTitles[position]
            }.attach()

            // Set up swipe-to-refresh
            binding.swipeRefreshLayout.setOnRefreshListener {
                refreshWhatsAppStatus()
            }
        }
    }

    fun refreshWhatsAppStatus() {
        Toast.makeText(activity, "Refreshing Whatsapp Status", Toast.LENGTH_SHORT).show()
//        viewModel.getWhatsAppStatus(viewLifecycleOwner) // Pass viewLifecycleOwner here as well
        viewModel.getWhatsAppStatus() // Pass viewLifecycleOwner here as well

    }

}
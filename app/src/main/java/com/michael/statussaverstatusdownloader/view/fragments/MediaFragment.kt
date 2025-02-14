package com.michael.statussaverstatusdownloader.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.FragmentMediaBinding
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.model.repository.SavedStatusRepository
import com.michael.statussaverstatusdownloader.model.repository.WBusinessStatusRepository
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.view.adapters.MediaAdapter
import com.michael.statussaverstatusdownloader.viewmodel.SavedStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.WBusinessStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.WhatsappStatusViewModel
import com.michael.statussaverstatusdownloader.viewmodel.factories.SavedStatusViewModelFactory
import com.michael.statussaverstatusdownloader.viewmodel.factories.WBusinessStatusViewModelFactory
import com.michael.statussaverstatusdownloader.viewmodel.factories.WhatsappStatusViewModelFactory


class MediaFragment : Fragment() {
    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!
    lateinit var whatsappStatusViewModel: WhatsappStatusViewModel
    lateinit var wBusinessStatusViewModel: WBusinessStatusViewModel
    lateinit var savedStatusViewModel: SavedStatusViewModel
    lateinit var adapter: MediaAdapter
    private val TAG = "MediaFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        adapter = MediaAdapter(ArrayList<MediaModel>(), requireActivity())
        binding.apply {
            arguments?.let {
                val whatsappStatusRepo = WhatsappStatusRepository(requireActivity())
                whatsappStatusViewModel = ViewModelProvider(
                    requireActivity(),
                    WhatsappStatusViewModelFactory(whatsappStatusRepo)
                )[WhatsappStatusViewModel::class.java]

                val wBusinessStatusRepo = WBusinessStatusRepository(requireActivity())
                wBusinessStatusViewModel = ViewModelProvider(
                    requireActivity(),
                    WBusinessStatusViewModelFactory(wBusinessStatusRepo)
                )[WBusinessStatusViewModel::class.java]

                val savedStatusRepo = SavedStatusRepository(requireActivity())
                savedStatusViewModel = ViewModelProvider(
                    requireActivity(),
                    SavedStatusViewModelFactory(savedStatusRepo)
                )[SavedStatusViewModel::class.java]

                val mediaType = it.getString(Constants.MEDIA_TYPE_KEY, "")
                Log.d(TAG, "onCreateView: Media type = $mediaType")

                // Load WhatsApp status data if the LiveData is empty
                if (whatsappStatusViewModel.getWhatsAppStatusLiveData().value.isNullOrEmpty()) {
//                    whatsappStatusViewModel.getWhatsAppStatus(viewLifecycleOwner)
                    whatsappStatusViewModel.getWhatsAppStatus()
                }

                if (wBusinessStatusViewModel.getWBusinessStatusLiveData().value.isNullOrEmpty()) {
                    wBusinessStatusViewModel.getWBusinessStatus()
                }

                if (savedStatusViewModel.getSavedStatusLiveData().value.isNullOrEmpty()) {
                    savedStatusViewModel.getSavedStatus()
                }

                setupStatusObservers(mediaType)


            }
        }
        return binding.root
    }


    private fun setupStatusObservers(mediaType: String) {
        when (mediaType) {

            Constants.MEDIA_TYPE_WHATSAPP_STATUS_IMAGES -> {
                whatsappStatusViewModel.whatsappStatusImagesLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing whatsappStatusImagesLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_WHATSAPP_STATUS_IMAGES)
                }
            }
            Constants.MEDIA_TYPE_WHATSAPP_STATUS_VIDEOS -> {
                whatsappStatusViewModel.whatsappStatusVideosLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing whatsappStatusVideosLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_WHATSAPP_STATUS_VIDEOS)
                }
            }
            Constants.MEDIA_TYPE_WHATSAPP_STATUS_AUDIOS -> {
                whatsappStatusViewModel.whatsappStatusAudiosLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing whatsappStatusAudiosLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_WHATSAPP_STATUS_AUDIOS)
                }
            }

            // WBusiness Observer:
            Constants.MEDIA_TYPE_W_BUSINESS_STATUS_IMAGES -> {
                wBusinessStatusViewModel.wBusinessStatusImagesLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing wBusinessStatusImagesLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_W_BUSINESS_STATUS_IMAGES)
                }
            }
            Constants.MEDIA_TYPE_W_BUSINESS_STATUS_VIDEOS -> {
                wBusinessStatusViewModel.wBusinessStatusVideosLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing wBusinessStatusVideosLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_WHATSAPP_STATUS_VIDEOS)
                }
            }
            Constants.MEDIA_TYPE_W_BUSINESS_STATUS_AUDIOS -> {
                wBusinessStatusViewModel.wBusinessStatusAudiosLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing wBusinessStatusAudiosLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_W_BUSINESS_STATUS_AUDIOS)
                }
            }

            // Saved Status Observer:
            // WBusiness Observer:
            Constants.MEDIA_TYPE_SAVED_STATUS_IMAGES -> {
                savedStatusViewModel.savedStatusImagesLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing savedStatusImagesLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_SAVED_STATUS_IMAGES)
                }
            }
            Constants.MEDIA_TYPE_SAVED_STATUS_VIDEOS -> {
                savedStatusViewModel.savedStatusVideosLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing savedStatusVideosLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_SAVED_STATUS_VIDEOS)
                }
            }
            Constants.MEDIA_TYPE_SAVED_STATUS_AUDIOS -> {
                savedStatusViewModel.savedStatusAudiosLiveData.observe(viewLifecycleOwner) { unSorted ->
                    Log.d(TAG, "Observing savedStatusAudiosLiveData: ${unSorted.size} items")
                    updateUI(unSorted, Constants.MEDIA_TYPE_SAVED_STATUS_AUDIOS)
                }
            }
        }
    }

    private fun updateUI(unSorted: List<MediaModel>, statusType: String) {
        val sortedList = unSorted.sortedByDescending { model ->
            model.fileDate
        }
        val list = ArrayList<MediaModel>()
        sortedList.forEach { model ->
            list.add(model)
        }

        // Clear the adapter's data before adding new items
        adapter.clearData()
        adapter = MediaAdapter(list, requireActivity())
        binding.mediaRecyclerView.adapter = adapter

        val type = when (statusType) {
            Constants.MEDIA_TYPE_WHATSAPP_STATUS_IMAGES -> getString(R.string.no_image_available_now)
            Constants.MEDIA_TYPE_WHATSAPP_STATUS_VIDEOS -> getString(R.string.no_video_available_now)
            Constants.MEDIA_TYPE_WHATSAPP_STATUS_AUDIOS -> getString(R.string.no_audio_available_now)
            Constants.MEDIA_TYPE_W_BUSINESS_STATUS_IMAGES -> getString(R.string.no_image_available_now)
            Constants.MEDIA_TYPE_W_BUSINESS_STATUS_VIDEOS -> getString(R.string.no_video_available_now)
            Constants.MEDIA_TYPE_W_BUSINESS_STATUS_AUDIOS -> getString(R.string.no_audio_available_now)
            Constants.MEDIA_TYPE_SAVED_STATUS_IMAGES -> getString(R.string.no_image_available_now)
            Constants.MEDIA_TYPE_SAVED_STATUS_VIDEOS -> getString(R.string.no_video_available_now)
            Constants.MEDIA_TYPE_SAVED_STATUS_AUDIOS -> getString(R.string.no_audio_available_now)
            else -> getString(R.string.no_media_available_yet)
        }
        if (list.isEmpty()) {
            binding.noMediaAvailable.noMediaTextView.text = type
            binding.noMediaHolder.visibility = View.VISIBLE
        } else {
            binding.noMediaHolder.visibility = View.GONE
        }

    }
}
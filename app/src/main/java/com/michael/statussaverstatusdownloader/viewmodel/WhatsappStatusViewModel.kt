package com.michael.statussaverstatusdownloader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WhatsappStatusViewModel(private val repository: WhatsappStatusRepository) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val TAG = "WhatsappStatusViewModel"

    private val whatsappStatusLiveData get() = repository.whatsappStatusLiveData

    val whatsappStatusImagesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsappStatusVideosLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val whatsappStatusAudiosLiveData = MutableLiveData<ArrayList<MediaModel>>()

    private var loadingJob: Job? = null

    init {
        // Observe the repository's LiveData permanently
        viewModelScope.launch {
            whatsappStatusLiveData.asFlow().collect { statusList ->
                processStatusList(statusList)
            }
        }
    }

    private fun processStatusList(statusList: ArrayList<MediaModel>) {
        val images = ArrayList<MediaModel>()
        val videos = ArrayList<MediaModel>()
        val audios = ArrayList<MediaModel>()

        statusList.forEach { mediaModel ->
            when (mediaModel.fileType) {
                Constants.MEDIA_TYPE_IMAGE -> {
                    images.add(mediaModel)
                    Log.d(TAG, "init: WhatsappStatusImage : ${mediaModel.fileName}")
                }

                Constants.MEDIA_TYPE_VIDEO -> {
                    videos.add(mediaModel)
                    Log.d(TAG, "init: WhatsappStatusVideo : ${mediaModel.fileName}")
                }

                Constants.MEDIA_TYPE_AUDIO -> {
                    audios.add(mediaModel)
                    Log.d(TAG, "init: WhatsappStatusAudio : ${mediaModel.fileName}")
                }
            }
        }

        // Post the split data to the respective LiveData
        Log.d(TAG, "init: images.size = ${images.size}")
        Log.d(TAG, "init: videos.size = ${videos.size}")
        Log.d(TAG, "init: audios.size = ${audios.size}")
        whatsappStatusImagesLiveData.postValue(images)
        whatsappStatusVideosLiveData.postValue(videos)
        whatsappStatusAudiosLiveData.postValue(audios)
    }

    fun getWhatsAppStatus() {
        if (_isLoading.value == true) return

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                withContext(Dispatchers.IO) {
                    repository.loadWhatsAppStatus()
                }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Create a public getter for the whatsappStatusLiveData
    fun getWhatsAppStatusLiveData(): LiveData<ArrayList<MediaModel>> {
        return whatsappStatusLiveData
    }

    override fun onCleared() {
        super.onCleared()
        loadingJob?.cancel()
    }
}
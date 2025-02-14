package com.michael.statussaverstatusdownloader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.model.repository.WBusinessStatusRepository
import com.michael.statussaverstatusdownloader.model.repository.WhatsappStatusRepository
import com.michael.statussaverstatusdownloader.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

class WBusinessStatusViewModel(private val repository: WBusinessStatusRepository) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val TAG = "WBusinessStatusViewModel"

    private val wBusinessStatusLiveData get() = repository.wBusinessStatusLiveData

    val wBusinessStatusImagesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val wBusinessStatusVideosLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val wBusinessStatusAudiosLiveData = MutableLiveData<ArrayList<MediaModel>>()

    private var loadingJob: Job? = null

    init {
        // Observe the repository's LiveData permanently
        viewModelScope.launch {
            wBusinessStatusLiveData.asFlow().collect { statusList ->
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
                    Log.d(TAG, "init: WBusinessStatusImage : ${mediaModel.fileName}")
                }

                Constants.MEDIA_TYPE_VIDEO -> {
                    videos.add(mediaModel)
                    Log.d(TAG, "init: WBusinessStatusVideo : ${mediaModel.fileName}")
                }

                Constants.MEDIA_TYPE_AUDIO -> {
                    audios.add(mediaModel)
                    Log.d(TAG, "init: WBusinessStatusAudio : ${mediaModel.fileName}")
                }
            }
        }

        // Post the split data to the respective LiveData
        Log.d(TAG, "init: images.size = ${images.size}")
        Log.d(TAG, "init: videos.size = ${videos.size}")
        Log.d(TAG, "init: audios.size = ${audios.size}")
        wBusinessStatusImagesLiveData.postValue(images)
        wBusinessStatusVideosLiveData.postValue(videos)
        wBusinessStatusAudiosLiveData.postValue(audios)
    }

    fun getWBusinessStatus() {
        if (_isLoading.value == true) return

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                withContext(Dispatchers.IO) {
                    repository.loadWBusinessStatus()
                }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Create a public getter for the wBusinessStatusLiveData
    fun getWBusinessStatusLiveData(): LiveData<ArrayList<MediaModel>> {
        return wBusinessStatusLiveData
    }

    override fun onCleared() {
        super.onCleared()
        loadingJob?.cancel()
    }
}
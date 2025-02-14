package com.michael.statussaverstatusdownloader.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.model.repository.SavedStatusRepository
import com.michael.statussaverstatusdownloader.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.forEach

class SavedStatusViewModel(private val repository: SavedStatusRepository) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val TAG = "SavedStatusViewModel"

    private val savedStatusLiveData get() = repository.savedStatusLiveData

    val savedStatusImagesLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val savedStatusVideosLiveData = MutableLiveData<ArrayList<MediaModel>>()
    val savedStatusAudiosLiveData = MutableLiveData<ArrayList<MediaModel>>()

    private var loadingJob: Job? = null

    init {
        // Observe the repository's LiveData permanently
        viewModelScope.launch {
            savedStatusLiveData.asFlow().collect { statusList ->
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
                    Log.d(TAG, "init: SavedStatusImage : ${mediaModel.fileName}")
                }

                Constants.MEDIA_TYPE_VIDEO -> {
                    videos.add(mediaModel)
                    Log.d(TAG, "init: SavedStatusVideo : ${mediaModel.fileName}")
                }

                Constants.MEDIA_TYPE_AUDIO -> {
                    audios.add(mediaModel)
                    Log.d(TAG, "init: SavedStatusAudio : ${mediaModel.fileName}")
                }
            }
        }

        // Post the split data to the respective LiveData
        Log.d(TAG, "init: images.size = ${images.size}")
        Log.d(TAG, "init: videos.size = ${videos.size}")
        Log.d(TAG, "init: audios.size = ${audios.size}")
        savedStatusImagesLiveData.postValue(images)
        savedStatusVideosLiveData.postValue(videos)
        savedStatusAudiosLiveData.postValue(audios)
    }

    fun getSavedStatus() {
        if (_isLoading.value == true) return

        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                withContext(Dispatchers.IO) {
                    repository.loadSavedStatus()
                }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // Create a public getter for the savedStatusLiveData
    fun getSavedStatusLiveData(): LiveData<ArrayList<MediaModel>> {
        return savedStatusLiveData
    }

    override fun onCleared() {
        super.onCleared()
        loadingJob?.cancel()
    }
}
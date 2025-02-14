package com.michael.statussaverstatusdownloader.model.repository

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.utils.getFileExtension
import com.michael.statussaverstatusdownloader.utils.isStatusExist

class SavedStatusRepository(context: Context) {
    val savedStatusLiveData = MutableLiveData<ArrayList<MediaModel>>()
    private val activity = context as Activity
    private val TAG = "SavedStatusRepository"

    fun loadSavedStatus() {
        val newSavedStatusList = ArrayList<MediaModel>()

        try {
            val mediaUri: Uri = Constants.SAVED_STATUS_PATH ?: run {
                Log.e(TAG, "Media path URI is null")
                savedStatusLiveData.postValue(newSavedStatusList)
                return
            }

            Log.d(TAG, "Media URI: $mediaUri")
            val mediaDocument = DocumentFile.fromTreeUri(activity, mediaUri)
            Log.d(TAG, "Media Document: $mediaDocument")

            if (mediaDocument == null || !mediaDocument.exists()) {
                Log.e(TAG, "Invalid or inaccessible media directory")
                savedStatusLiveData.postValue(newSavedStatusList)
                return
            }

            // Ensure the directory is accessible
            if (!mediaDocument.isDirectory) {
                Log.e(TAG, "The provided URI does not point to a directory")
                savedStatusLiveData.postValue(newSavedStatusList)
                return
            }

            var count = 1
            mediaDocument.listFiles().forEach { file ->
                try {
                    if (file?.name == null || !file.isFile || file.name == ".nomedia") {
                        return@forEach
                    }

                    Log.d(TAG, "Processing saved status: ${file.name} : ${count++}")

                    val fileType = when (getFileExtension(file.name!!).lowercase()) {
                        "mp4" -> Constants.MEDIA_TYPE_VIDEO
                        "jpg", "jpeg", "png" -> Constants.MEDIA_TYPE_IMAGE
                        "opus", "mp3" -> Constants.MEDIA_TYPE_AUDIO
                        else -> {
                            Log.d(TAG, "Unknown file type: ${file.name}")
                            Constants.MEDIA_TYPE_UNKNOWN
                        }
                    }

                    newSavedStatusList.add(
                        MediaModel(
                            pathUri = file.uri.toString(),
                            fileName = file.name!!,
                            fileType = fileType,
                            isSaved = activity.isStatusExist(file.name!!),
                            fileDate = file.lastModified()
                        )
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing saved status file: ${file?.name}", e)
                }
            }

            Log.d(TAG, "Loaded ${newSavedStatusList.size} saved status items")
            savedStatusLiveData.postValue(newSavedStatusList)

        } catch (e: Exception) {
            Log.e(TAG, "Error loading saved statuses", e)
            savedStatusLiveData.postValue(newSavedStatusList)
        }
    }
}
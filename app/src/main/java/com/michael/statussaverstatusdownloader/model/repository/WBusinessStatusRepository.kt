package com.michael.statussaverstatusdownloader.model.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.MutableLiveData
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.utils.getFileExtension
import com.michael.statussaverstatusdownloader.utils.isStatusExist

class WBusinessStatusRepository(context: Context) {
    val wBusinessStatusLiveData = MutableLiveData<ArrayList<MediaModel>>()
    private val activity = context as Activity
    private val TAG = "WBusinessStatusRepository"

    fun loadWBusinessStatus() {
        val newWBusinessStatusList = ArrayList<MediaModel>()

        try {
            val mediaUri = Constants.MEDIA_PATH_URI ?: run {
                Log.e(TAG, "Media path URI is null")
                wBusinessStatusLiveData.postValue(newWBusinessStatusList)
                return
            }

            val mediaDocument = DocumentFile.fromTreeUri(activity, mediaUri)
            if (mediaDocument == null || !mediaDocument.exists()) {
                Log.e(TAG, "Invalid or inaccessible media directory")
                wBusinessStatusLiveData.postValue(newWBusinessStatusList)
                return
            }

            val wBusinessDocument = findWBusinessStatusDirectory(mediaDocument)
            if (wBusinessDocument == null) {
                Log.e(TAG, ".Statuses directory not found")
                wBusinessStatusLiveData.postValue(newWBusinessStatusList)
                return
            }

            var count = 1
            wBusinessDocument.listFiles().forEach { file ->
                try {
                    // Null check for file
                    if (file?.name == null || !file.isFile || file.name == ".nomedia") {
                        return@forEach
                    }

                    Log.d(TAG, "Processing whatsapp business status: ${file.name} : ${count++}")

                    val fileType = when (getFileExtension(file.name!!).lowercase()) {
                        "mp4" -> Constants.MEDIA_TYPE_VIDEO
                        "jpg", "jpeg", "png" -> Constants.MEDIA_TYPE_IMAGE
                        "opus", "mp3" -> Constants.MEDIA_TYPE_AUDIO
                        else -> {
                            Log.d(TAG, "Unknown file type: ${file.name}")
                            Constants.MEDIA_TYPE_UNKNOWN
                        }
                    }

                    newWBusinessStatusList.add(
                        MediaModel(
                            pathUri = file.uri.toString(),
                            fileName = file.name!!,
                            fileType = fileType,
                            isSaved = activity.isStatusExist(file.name!!),
                            fileDate = file.lastModified()
                        )
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing WhatsApp Status file: ${file?.name}", e)
                    // Continue processing other files
                }
            }

            Log.d(TAG, "Loaded ${newWBusinessStatusList.size} status items")
            wBusinessStatusLiveData.postValue(newWBusinessStatusList)

        } catch (e: Exception) {
            Log.e(TAG, "Error loading WhatsApp status", e)
            wBusinessStatusLiveData.postValue(newWBusinessStatusList)
        }
    }

    private fun findWBusinessStatusDirectory(mediaDocument: DocumentFile): DocumentFile? {
        try {
            mediaDocument.listFiles().forEach { dir ->
                // Check for WhatsApp Business directory
                if (dir?.isDirectory == true && dir.name == "com.whatsapp.w4b") {
                    dir.listFiles().forEach { subDir ->
                        // Check for the "WhatsApp Business" directory inside WhatsApp Business
                        if (subDir?.isDirectory == true && subDir.name == "WhatsApp Business") {
                            subDir.listFiles().forEach { subSubDir ->
                                // Check for the "Media" directory
                                if (subSubDir?.isDirectory == true && subSubDir.name == "Media") {
                                    subSubDir.listFiles().forEach { statusDir ->
                                        // Check for the ".Statuses" directory
                                        if (statusDir?.isDirectory == true && statusDir.name == ".Statuses") {
                                            return statusDir
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error finding WhatsApp Business status directory", e)
        }
        return null
    }
}


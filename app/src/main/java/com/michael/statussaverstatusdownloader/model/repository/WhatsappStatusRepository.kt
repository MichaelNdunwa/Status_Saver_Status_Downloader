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

//class WhatsappStatusRepository(context: Context) {
//
//    val whatsappStatusLiveData = MutableLiveData<ArrayList<MediaModel>>()
//    private val whatsappStatusList = ArrayList<MediaModel>()
//    val activity = context as Activity
//    private val TAG = "WhatsappStatusRepository"
//
//    fun loadWhatsAppStatus() {
//        // Clear the existing list before loading new items
//        whatsappStatusList.clear()
//
//        // Get the Uri for the media directory (saved from the folder picker)
//        val mediaUri = Constants.MEDIA_PATH_URI
//
//        // Create a DocumentFile for the media directory
//        val mediaDocument = DocumentFile.fromTreeUri(activity, mediaUri)
//        if (mediaDocument == null || !mediaDocument.exists()) {
//            Log.d(TAG, "Invalid or inaccessible media directory")
//            return
//        }
//
//        // Navigate to the .Statuses directory
//        val whatsappDocument = findWhatsAppStatusDirectory(mediaDocument)
//        if (whatsappDocument == null) {
//            Log.d(TAG, ".Statuses directory not found")
//            return
//        }
//
//        // List files in the .Statuses directory
//        var count = 1
////        val processedFiles = mutableSetOf<String>()
//        whatsappDocument.listFiles().forEach { file ->
//            if (file.name != ".nomedia" && file.isFile) {
//                Log.d(TAG, "loadWhatsAppStatus: ${file.name} : ${count++}")
////                val isSaved = activity.isStatusExist(file.name.toString())
//                val fileName = file.name!!
//
//                // Skip if we have already processed this file
////                if (processedFiles.contains(fileName)) return@forEach
////                processedFiles.add(fileName)
//
//                val fileType = when (getFileExtension(file.name ?: "")) {
//                    "mp4" -> Constants.MEDIA_TYPE_VIDEO
//                    "jpg", "jpeg", "png" -> Constants.MEDIA_TYPE_IMAGE
//                    "opus", "mp3" -> Constants.MEDIA_TYPE_AUDIO
//                    else -> Constants.MEDIA_TYPE_UNKNOWN
//                }
//                whatsappStatusList.add(
//                    MediaModel(
//                        pathUri = file.uri.toString(),
////                        fileName = file.name ?: "",
//                        fileName = fileName,
//                        fileType = fileType,
////                        isSaved = isSaved,
//                        isSaved = activity.isStatusExist(fileName),
//                        fileDate = file.lastModified()
//                    )
//                )
//            }
//        }
//        Log.d(TAG, "loadWhatsAppStatus: Posting whatsappStatusList  whatsappStatusList.size = ${whatsappStatusList.size}")
//        whatsappStatusLiveData.postValue(whatsappStatusList)
//    }
//
//    private fun findWhatsAppStatusDirectory(mediaDocument: DocumentFile): DocumentFile? {
//        // Traverse the media directory to find the .Statuses directory
//        mediaDocument.listFiles().forEach { dir ->
//            if (dir.isDirectory && dir.name == "com.whatsapp") {
//                dir.listFiles().forEach { subDir ->
//                    if (subDir.isDirectory && subDir.name == "WhatsApp") {
//                        subDir.listFiles().forEach { subSubDir ->
//                            if (subSubDir.isDirectory && subSubDir.name == "Media") {
//                                subSubDir.listFiles().forEach { statusDir ->
//                                    if (statusDir.isDirectory && statusDir.name == ".Statuses") {
//                                        return statusDir
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null
//    }
//}




//class WhatsappStatusRepository(context: Context) {
//
//    val whatsappStatusLiveData = MutableLiveData<ArrayList<MediaModel>>()
//    private val activity = context as Activity
//    private val TAG = "WhatsappStatusRepository"
//
//    fun loadWhatsAppStatus() {
//        val newWhatsappStatusList = ArrayList<MediaModel>() // Create a *new* list
//
//        newWhatsappStatusList.clear()
//
//        val mediaUri = Constants.MEDIA_PATH_URI
//
//        val mediaDocument = DocumentFile.fromTreeUri(activity, mediaUri)
//        if (mediaDocument == null || !mediaDocument.exists()) {
//            Log.d(TAG, "Invalid or inaccessible media directory")
//            whatsappStatusLiveData.postValue(newWhatsappStatusList) // Post empty list in case of error
//            return
//        }
//
//        val whatsappDocument = findWhatsAppStatusDirectory(mediaDocument)
//        if (whatsappDocument == null) {
//            Log.d(TAG, ".Statuses directory not found")
//            whatsappStatusLiveData.postValue(newWhatsappStatusList) // Post empty list in case of error
//            return
//        }
//
//        var count = 1
//        whatsappDocument.listFiles().forEach { file ->
//            if (file.name != ".nomedia" && file.isFile) {
//                Log.d(TAG, "loadWhatsAppStatus: ${file.name} : ${count++}")
//                val fileName = file.name!!
//
//                val fileType = when (getFileExtension(file.name ?: "")) {
//                    "mp4" -> Constants.MEDIA_TYPE_VIDEO
//                    "jpg", "jpeg", "png" -> Constants.MEDIA_TYPE_IMAGE
//                    "opus", "mp3" -> Constants.MEDIA_TYPE_AUDIO
//                    else -> Constants.MEDIA_TYPE_UNKNOWN
//                }
//
//                newWhatsappStatusList.add( // Add to the *new* list
//                    MediaModel(
//                        pathUri = file.uri.toString(),
//                        fileName = fileName,
//                        fileType = fileType,
//                        isSaved = activity.isStatusExist(fileName),
//                        fileDate = file.lastModified()
//                    )
//                )
//            }
//        }
//
//        whatsappStatusLiveData.postValue(newWhatsappStatusList) // Post the *new* list
//    }
//
//    private fun findWhatsAppStatusDirectory(mediaDocument: DocumentFile): DocumentFile? {
//        mediaDocument.listFiles().forEach { dir ->
//            if (dir.isDirectory && dir.name == "com.whatsapp") {
//                dir.listFiles().forEach { subDir ->
//                    if (subDir.isDirectory && subDir.name == "WhatsApp") {
//                        subDir.listFiles().forEach { subSubDir ->
//                            if (subSubDir.isDirectory && subSubDir.name == "Media") {
//                                subSubDir.listFiles().forEach { statusDir ->
//                                    if (statusDir.isDirectory && statusDir.name == ".Statuses") {
//                                        return statusDir
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null
//    }
//}







class WhatsappStatusRepository(context: Context) {
    val whatsappStatusLiveData = MutableLiveData<ArrayList<MediaModel>>()
    private val activity = context as Activity
    private val TAG = "WhatsappStatusRepository"

    fun loadWhatsAppStatus() {
        val newWhatsappStatusList = ArrayList<MediaModel>()

        try {
            val mediaUri = Constants.MEDIA_PATH_URI ?: run {
                Log.e(TAG, "Media path URI is null")
                whatsappStatusLiveData.postValue(newWhatsappStatusList)
                return
            }

            val mediaDocument = DocumentFile.fromTreeUri(activity, mediaUri)
            if (mediaDocument == null || !mediaDocument.exists()) {
                Log.e(TAG, "Invalid or inaccessible media directory")
                whatsappStatusLiveData.postValue(newWhatsappStatusList)
                return
            }

            val whatsappDocument = findWhatsAppStatusDirectory(mediaDocument)
            if (whatsappDocument == null) {
                Log.e(TAG, ".Statuses directory not found")
                whatsappStatusLiveData.postValue(newWhatsappStatusList)
                return
            }

            var count = 1
            whatsappDocument.listFiles().forEach { file ->
                try {
                    // Null check for file
                    if (file?.name == null || !file.isFile || file.name == ".nomedia") {
                        return@forEach
                    }

                    Log.d(TAG, "Processing whatsapp status: ${file.name} : ${count++}")

                    val fileType = when (getFileExtension(file.name!!).lowercase()) {
                        "mp4" -> Constants.MEDIA_TYPE_VIDEO
                        "jpg", "jpeg", "png" -> Constants.MEDIA_TYPE_IMAGE
                        "opus", "mp3" -> Constants.MEDIA_TYPE_AUDIO
                        else -> {
                            Log.d(TAG, "Unknown file type: ${file.name}")
                            Constants.MEDIA_TYPE_UNKNOWN
                        }
                    }

                    newWhatsappStatusList.add(
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

            Log.d(TAG, "Loaded ${newWhatsappStatusList.size} status items")
            whatsappStatusLiveData.postValue(newWhatsappStatusList)

        } catch (e: Exception) {
            Log.e(TAG, "Error loading WhatsApp status", e)
            whatsappStatusLiveData.postValue(newWhatsappStatusList)
        }
    }

    private fun findWhatsAppStatusDirectory(mediaDocument: DocumentFile): DocumentFile? {
        try {
            mediaDocument.listFiles().forEach { dir ->
                if (dir?.isDirectory == true && dir.name == "com.whatsapp") {
                    dir.listFiles().forEach { subDir ->
                        if (subDir?.isDirectory == true && subDir.name == "WhatsApp") {
                            subDir.listFiles().forEach { subSubDir ->
                                if (subSubDir?.isDirectory == true && subSubDir.name == "Media") {
                                    subSubDir.listFiles().forEach { statusDir ->
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
            Log.e(TAG, "Error finding WhatsApp status directory", e)
        }
        return null
    }
}






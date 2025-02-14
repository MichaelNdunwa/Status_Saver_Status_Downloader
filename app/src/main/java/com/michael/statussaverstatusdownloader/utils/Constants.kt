package com.michael.statussaverstatusdownloader.utils

import android.net.Uri
import android.os.Build

object Constants {

    // Themes
    const val SYSTEM_DEFAULT_THEME = 0
    const val LIGHT_THEME = 1
    const val DARK_THEME = 2

    // Request Code
    const val STATUS_REQUEST_CODE = 100

    // Media Types
    const val MEDIA_TYPE_IMAGE = "image"
    const val MEDIA_TYPE_VIDEO = "video"
    const val MEDIA_TYPE_AUDIO = "audio"
    const val MEDIA_TYPE_UNKNOWN = "unknown"

    // Media Keys
    const val MEDIA_LIST_KEY = "media_list"
    const val MEDIA_SCROLL_KEY = "media_scroll"
    const val MEDIA_TYPE_KEY = "media_type"

    // Whatsapp Status Types
    const val MEDIA_TYPE_WHATSAPP_STATUS_IMAGES = "com.whatsapp.images"
    const val MEDIA_TYPE_WHATSAPP_STATUS_VIDEOS = "com.whatsapp.videos"
    const val MEDIA_TYPE_WHATSAPP_STATUS_AUDIOS = "com.whatsapp.audios"

    // WBusiness Status Types
    const val MEDIA_TYPE_W_BUSINESS_STATUS_IMAGES = "com.whatsapp.w4b.images"
    const val MEDIA_TYPE_W_BUSINESS_STATUS_VIDEOS = "com.whatsapp.w4b.videos"
    const val MEDIA_TYPE_W_BUSINESS_STATUS_AUDIOS = "com.whatsapp.w4b.audios"

    // Saved Status Types
    const val MEDIA_TYPE_SAVED_STATUS_IMAGES = "download.images"
    const val MEDIA_TYPE_SAVED_STATUS_VIDEOS = "download.videos"
    const val MEDIA_TYPE_SAVED_STATUS_AUDIOS = "download.audios"



    // URI Path Permissions

    val MEDIA_PATH_URI = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia") // I added "tree" in the path.


    // status path URI:
    val STATUS_PATH_URI_ANDROID =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia")


    // whatsApp status path URI:
    val WHATSAPP_PATH_URI_ANDROID =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AWhatsApp%2AWhatsApp%2FMedia%2F.Statuses")
    val WHATSAPP_PATH_URI_ANDROID_11 =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")
    val WHATSAPP_STATUS_PATH =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")

    // whatsApp Business status path URI:
    val W_BUSINESS_PATH_URI_ANDROID =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AWhatsApp%20Business%2FMedia%2F.Statuses")
    val W_BUSINESS_PATH_URI_ANDROID_11 =
        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses")
    val W_BUSINESS_STATUS_PATH = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses")

    // saved status paths URI:
//    val SAVED_PATH_URI_ANDROID = listOf(
//        Uri.parse("content://com.android.externalstorage.documents/document/primary%3APictures%2FStatus%20Saver"), // For images and videos
//        Uri.parse("content://com.android.externalstorage.documents/document/primary%3AMusic%2FStatus%20Saver")     // For audio
//    )

//    val SAVED_STATUS_PATH = Uri.parse("content://com.android.externalstorage.documents/document/primary%3ADownload") // Save path for all status
    val SAVED_STATUS_PATH = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3ADownload/document/primary%3ADownload%2FStatus%20Saver") // Save path for all status



//    fun getWhatsAppStatusPathUri(): Uri {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            WHATSAPP_PATH_URI_ANDROID_11
//        } else {
//            WHATSAPP_PATH_URI_ANDROID
//        }
//    }
//    fun getWBusinessStatusPathUri(): Uri {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            W_BUSINESS_PATH_URI_ANDROID_11
//        } else {
//            W_BUSINESS_PATH_URI_ANDROID
//        }
//    }
//    fun getSavedStatusPathUri(): List<Uri> = SAVED_PATH_URI_ANDROID

//    fun getAllPathUri(): List<Uri> {
//        val pathUriList = mutableListOf<Uri>()
//        pathUriList.add(getWhatsAppStatusPathUri())
//        pathUriList.add(getWBusinessStatusPathUri())
//        pathUriList.addAll(getSavedStatusPathUri())
//        return pathUriList
//    }

//    fun getAllPathUri(): List<Uri> {
//        val pathUriList = mutableListOf<Uri>()
//        pathUriList.add(STATUS_PATH_URI_ANDROID)
//        pathUriList.add(SAVED_PATH_URI_ANDROID)
//        return pathUriList
//    }

}
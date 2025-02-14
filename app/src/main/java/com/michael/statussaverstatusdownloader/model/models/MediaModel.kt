package com.michael.statussaverstatusdownloader.model.models

import android.os.Parcelable
import com.michael.statussaverstatusdownloader.utils.Constants
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MediaModel(
    val pathUri: String,
    val fileName: String,
    val fileType: String = Constants.MEDIA_TYPE_IMAGE,
    var isSaved: Boolean = false,
    val fileDate: Long,
): Parcelable
package com.michael.statussaverstatusdownloader.view.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.ItemMediaBinding
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.utils.Constants.MEDIA_TYPE_IMAGE
import com.michael.statussaverstatusdownloader.utils.Constants.MEDIA_TYPE_VIDEO
import com.michael.statussaverstatusdownloader.utils.isStatusExist
import com.michael.statussaverstatusdownloader.utils.saveStatus
import com.michael.statussaverstatusdownloader.view.activities.AudioPlayerActivity
import com.michael.statussaverstatusdownloader.view.activities.ImageViewerActivity
import com.michael.statussaverstatusdownloader.view.activities.VideoPlayerActivity

class MediaAdapter(private var list: ArrayList<MediaModel>, val context: Context) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {
    val TAG = "MediaAdapter"


    inner class ViewHolder(val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(medialModel: MediaModel) {
            binding.apply {
                // Load image and video into image view
                Glide.with(context).load(medialModel.pathUri.toUri()).into(statusImage)

                // Load custom image into the audio status
                if (medialModel.fileType == Constants.MEDIA_TYPE_AUDIO) {
                    Glide.with(context).load(R.drawable.audio_status_img).into(statusImage)
                }

                // hide play button for images
                if (medialModel.fileType == Constants.MEDIA_TYPE_IMAGE) {
                    playButton.visibility = View.GONE
                }

                // show download check if media is download
                if (context.isStatusExist(medialModel.fileName)) {
                    downloadText.visibility = View.GONE
                    downloadCheck.visibility = View.VISIBLE
                    medialModel.isSaved = true
                } else {
                    downloadText.visibility = View.VISIBLE
                    downloadCheck.visibility = View.GONE
                    medialModel.isSaved = false
                }

                // set click listener for status card:
                statusCard.setOnClickListener {
                    Log.d(TAG, "bind: Clicked on ${medialModel.fileName}")
                    if (medialModel.fileType == MEDIA_TYPE_IMAGE) {
                        // go to image viewer; ImageViewerActivity
                        Intent().apply {
//                            putExtra(Constants.MEDIA_LIST_KEY, list)
                            putParcelableArrayListExtra(Constants.MEDIA_LIST_KEY, list)
                            putExtra(Constants.MEDIA_SCROLL_KEY, layoutPosition)
                            setClass(context, ImageViewerActivity::class.java)
                            context.startActivity(this)
                        }
                    } else if (medialModel.fileType == MEDIA_TYPE_VIDEO) {
                        // go to video and audio player activity
                        Intent().apply {
//                            putExtra(Constants.MEDIA_LIST_KEY, list)
                            putParcelableArrayListExtra(Constants.MEDIA_LIST_KEY, list)
                            putExtra(Constants.MEDIA_SCROLL_KEY, layoutPosition)
                            setClass(context, VideoPlayerActivity::class.java)
                            context.startActivity(this)
                        }
                    } else {
                        Intent().apply {
//                            putExtra(Constants.MEDIA_LIST_KEY, list)
                            putParcelableArrayListExtra(Constants.MEDIA_LIST_KEY, list)
                            putExtra(Constants.MEDIA_SCROLL_KEY, layoutPosition)
                            setClass(context, AudioPlayerActivity::class.java)
                            context.startActivity(this)
                        }
                    }

                }

                // set click listener for download button
                downloadHolder.setOnClickListener {
                    val save = context.saveStatus(medialModel)
                    if (save) {
                        medialModel.isSaved = true
                        downloadCheck.visibility = View.VISIBLE
                        downloadText.visibility = View.GONE
                    } else {
                        // failed to save status
                        Toast.makeText(context, "Failed to save", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.ViewHolder {
        return ViewHolder(ItemMediaBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MediaAdapter.ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
        Log.d(TAG, "onBindViewHolder: ${model.fileName}")
    }

    override fun getItemCount(): Int = list.size

    fun clearData() {
        list = ArrayList()
        notifyDataSetChanged()
    }
}
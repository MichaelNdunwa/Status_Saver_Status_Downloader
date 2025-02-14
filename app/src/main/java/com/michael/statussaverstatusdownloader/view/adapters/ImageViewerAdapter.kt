package com.michael.statussaverstatusdownloader.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.isStatusExist
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.ItemImageViewerBinding
import com.michael.statussaverstatusdownloader.utils.saveStatus

class ImageViewerAdapter(val list: ArrayList<MediaModel>, val context: Context) :
    RecyclerView.Adapter<ImageViewerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemImageViewerBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: ItemImageViewerBinding) : RecyclerView.ViewHolder(binding.root) {
           fun bind(mediaModel: MediaModel)  {
               binding.apply {
                   Glide.with(context).load(mediaModel.pathUri.toUri()).into(zoomableImageView)
                   val downloadImage = if (context.isStatusExist(mediaModel.fileName)) R.drawable.icon_check else R.drawable.icon_download_2
                   val downloadText = if (context.isStatusExist(mediaModel.fileName)) "Saved" else "Download"
                   tools.downloadIcon.setImageResource(downloadImage)
                   tools.downloadTextview.text = downloadText

                   tools.downloadButton.setOnClickListener {
                       val isDownloaded = context.saveStatus(mediaModel)
                       if (isDownloaded) {
                           mediaModel.isSaved = true
                           tools.downloadIcon.setImageResource(R.drawable.icon_check)
                           tools.downloadTextview.text = "Saved"
                       } else {
                           Toast.makeText(context, "Failed to save", Toast.LENGTH_SHORT).show()
                       }
                   }

                   // Share button:
                   tools.shareButton.setOnClickListener {
                       val intent = Intent(Intent.ACTION_SEND)
                       intent.type = "image/*"
                       intent.putExtra(Intent.EXTRA_STREAM, mediaModel.pathUri.toUri())
                       intent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome status")
                       context.startActivity(Intent.createChooser(intent, "Share via"))
                   }

                   // Repost button:
                   tools.repostButton.setOnClickListener {
                       val intent = Intent(Intent.ACTION_SEND)
                       intent.type = "image/*"
                       intent.setPackage("com.whatsapp")
                       intent.putExtra(Intent.EXTRA_STREAM, mediaModel.pathUri.toUri())
                       intent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome status")
                       context.startActivity(intent)
                   }
               }
           }
        }
}
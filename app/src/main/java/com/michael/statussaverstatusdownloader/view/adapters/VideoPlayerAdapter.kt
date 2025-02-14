package com.michael.statussaverstatusdownloader.view.adapters

import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.ItemVideoPlayerBinding
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.saveStatus

class MediaPlayerAdapter(
    private val list: ArrayList<MediaModel>,
    private val context: Context
) : RecyclerView.Adapter<MediaPlayerAdapter.ViewHolder>() {

    private var currentPlayingPosition = RecyclerView.NO_POSITION
    private val viewHolders = SparseArray<ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVideoPlayerBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewHolders.put(position, holder)
        val model = list[position]
        holder.bind(model)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        val position = holder.bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            viewHolders.remove(position)
        }
        holder.releasePlayer()
    }

    override fun getItemCount(): Int = list.size

    fun playVideoAtPosition(position: Int) {
        if (position == currentPlayingPosition) return

        // Pause all other videos
        pauseAllPlayers()

        // Play the new video
        viewHolders[position]?.apply {
            preparePlayer()
            startPlayer()
            currentPlayingPosition = position
        }
    }

    fun pauseAllPlayers() {
        for (i in 0 until viewHolders.size()) {
            viewHolders.valueAt(i).pausePlayer()
        }
        currentPlayingPosition = RecyclerView.NO_POSITION
    }

    fun releaseAllPlayers() {
        for (i in 0 until viewHolders.size()) {
            viewHolders.valueAt(i).releasePlayer()
        }
        currentPlayingPosition = RecyclerView.NO_POSITION
    }

    inner class ViewHolder(private val binding: ItemVideoPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var player: ExoPlayer? = null
        private var isPlayerPrepared = false
        private var mediaModel: MediaModel? = null

        fun bind(model: MediaModel) {
            this.mediaModel = model

            // implement action buttons
            binding.apply {
                val downloadImage = if (mediaModel?.isSaved == true) {
                    R.drawable.icon_check
                } else {
                    R.drawable.icon_download_2
                }
                val downloadText = if (mediaModel?.isSaved == true) {
                    "Saved"
                } else {
                    "Download"
                }
                tools.downloadIcon.setImageResource(downloadImage)
                tools.downloadTextview.text = downloadText
                tools.downloadButton.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel!!)
                    if (isDownloaded) {
                        mediaModel?.isSaved = true
                        tools.downloadIcon.setImageResource(R.drawable.icon_check)
                        tools.downloadTextview.text = "Saved"
                    } else {
                        Toast.makeText(context, "Unable to Save", Toast.LENGTH_SHORT).show()
                    }
                }

                // Share button:
                tools.shareButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "video/*"
                    intent.putExtra(Intent.EXTRA_STREAM, mediaModel?.pathUri?.toUri())
                    intent.putExtra(Intent.EXTRA_TEXT, "Check out this video")
                    context.startActivity(Intent.createChooser(intent, "Share with"))
                }

                // Repost button:
                tools.repostButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "video/*"
                    intent.setPackage("com.whatsapp")
                    intent.putExtra(Intent.EXTRA_STREAM, mediaModel?.pathUri?.toUri())
                    context.startActivity(intent)
                }
            }
        }

        fun preparePlayer() {
            if (isPlayerPrepared) return

            player = ExoPlayer.Builder(context).build().apply {
                // Prevent automatic playback on prepare
                playWhenReady = false

                // Set repeat mode
                repeatMode = Player.REPEAT_MODE_ONE

                // Configure player
                binding.playerView.player = this

                // Load media
                mediaModel?.let { model ->
                    setMediaItem(MediaItem.fromUri(model.pathUri))
                    prepare()
                }
            }

            isPlayerPrepared = true
        }

        fun startPlayer() {
            if (!isPlayerPrepared) preparePlayer()
            player?.play()
        }

        fun pausePlayer() {
            player?.pause()
        }

        fun releasePlayer() {
            player?.release()
            player = null
            isPlayerPrepared = false
        }
    }
}
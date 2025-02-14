package com.michael.statussaverstatusdownloader.view.activities

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.michael.statussaverstatusdownloader.databinding.ActivityAudioPlayerBinding
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.view.adapters.AudioPlayerAdapter
import com.michael.statussaverstatusdownloader.view.adapters.MediaPlayerAdapter

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var adapter: AudioPlayerAdapter
    private var currentScrollPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(toolbar)
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val list = if (Build.VERSION.SDK_INT >= 33) {
                intent.getParcelableArrayListExtra(Constants.MEDIA_LIST_KEY, MediaModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableArrayListExtra(Constants.MEDIA_LIST_KEY)
            } ?: ArrayList()

            currentScrollPosition = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY, 0)

            adapter = AudioPlayerAdapter(list, this@AudioPlayerActivity)
            videoRecyclerView.adapter = adapter

            // Use PagerSnapHelper for snapping to full items
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(videoRecyclerView)

            // Initial scroll position
            videoRecyclerView.scrollToPosition(currentScrollPosition)

            // Add scroll listener with visibility detection
            videoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        updatePlaybackBasedOnVisibility()
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // Continuously update during scroll to ensure smooth transitions
                    updatePlaybackBasedOnVisibility()
                }
            })
        }

        // Add lifecycle observer to handle app background/foreground transitions
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                adapter.pauseAllPlayers()
            }

            override fun onResume(owner: LifecycleOwner) {
                updatePlaybackBasedOnVisibility()
            }
        })
    }

    private fun updatePlaybackBasedOnVisibility() {
        val layoutManager = binding.videoRecyclerView.layoutManager as? LinearLayoutManager ?: return

        // Calculate visible items
        val firstVisible = layoutManager.findFirstVisibleItemPosition()
        val lastVisible = layoutManager.findLastVisibleItemPosition()

        // Find the most visible item
        var maxVisibleItem = firstVisible
        var maxVisibility = 0f

        for (i in firstVisible..lastVisible) {
            val view = layoutManager.findViewByPosition(i) ?: continue
            val visibility = calculateVisibility(view)
            if (visibility > maxVisibility) {
                maxVisibility = visibility
                maxVisibleItem = i
            }
        }

        // Only play if the item is mostly visible (> 50%)
        if (maxVisibility > 0.5f) {
            adapter.playAudioAtPosition(maxVisibleItem)
        } else {
            adapter.pauseAllPlayers()
        }
    }

    private fun calculateVisibility(view: View): Float {
        val rect = Rect()
        if (!view.getGlobalVisibleRect(rect)) return 0f

        val visibleHeight = rect.height().toFloat()
        val totalHeight = view.height.toFloat()
        return visibleHeight / totalHeight
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.releaseAllPlayers()
    }
}
package com.michael.statussaverstatusdownloader.view.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.ActivityImageViewerBinding
import com.michael.statussaverstatusdownloader.model.models.MediaModel
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.view.adapters.ImageViewerAdapter
import java.util.ArrayList

class ImageViewerActivity : AppCompatActivity() {
    private val TAG = "ImageViewerActivity"
    private lateinit var binding: ActivityImageViewerBinding
    private lateinit var adapter: ImageViewerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_image_viewer)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        binding.apply {
            // setup toolbar
            setSupportActionBar(toolbar)

            // setup image statuses
//            val list = intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as ArrayList<MediaModel>
            val list = if (Build.VERSION.SDK_INT >= 33) {
               intent.getParcelableArrayListExtra(Constants.MEDIA_LIST_KEY, MediaModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableArrayListExtra(Constants.MEDIA_LIST_KEY)
            } ?: ArrayList()
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY, 0)
            adapter = ImageViewerAdapter(list, this@ImageViewerActivity)
            imageViewPager.adapter = adapter
            imageViewPager.setCurrentItem(scrollTo, false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
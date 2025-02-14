package com.michael.statussaverstatusdownloader.view.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.view.fragments.MediaFragment

class MediaViewPagerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val imagesType: String,
    private val videosType: String,
    private val audiosType: String
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                // Image Tab:
                val mediaFragment = MediaFragment()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_TYPE_KEY, imagesType)
                mediaFragment.arguments = bundle
                mediaFragment
            }

            1 -> {
                // Video Tab:
                val mediaFragment = MediaFragment()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_TYPE_KEY, videosType)
                mediaFragment.arguments = bundle
                mediaFragment
            }

            else -> {
                // Audio Tab:
                val mediaFragment = MediaFragment()
                val bundle = Bundle()
                bundle.putString(Constants.MEDIA_TYPE_KEY, audiosType)
                mediaFragment.arguments = bundle
                mediaFragment
            }
        }

    }
}
package com.michael.statussaverstatusdownloader.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.michael.statussaverstatusdownloader.R

fun Activity.loadFragment(fragment: Fragment) {
    val fragmentActivity: FragmentActivity = this as FragmentActivity
    fragmentActivity.supportFragmentManager.beginTransaction().apply {
        setReorderingAllowed(true)
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        replace(R.id.frame_layout, fragment)
        addToBackStack(null)
    }.commit()
}
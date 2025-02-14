package com.michael.statussaverstatusdownloader.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.ActivityMainBinding
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.utils.FolderPermissionHandler
import com.michael.statussaverstatusdownloader.utils.SharedPrefKeys
import com.michael.statussaverstatusdownloader.utils.SharedPreferenceUtils
import com.michael.statussaverstatusdownloader.utils.ThemeUtils
import com.michael.statussaverstatusdownloader.utils.loadFragment
import com.michael.statussaverstatusdownloader.view.fragments.SavedFragment
import com.michael.statussaverstatusdownloader.view.fragments.WBusinessFragment
import com.michael.statussaverstatusdownloader.view.fragments.WhatsappFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activity = this@MainActivity
    private lateinit var folderPermissionHandler: FolderPermissionHandler
    private lateinit var uri: Uri
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize SharedPreferences
        SharedPreferenceUtils.init(activity)

        // Apply theme
        ThemeUtils.applyTheme(ThemeUtils.getTheme())

        // Permission
        folderPermissionHandler = FolderPermissionHandler(activity as AppCompatActivity)
        uri = Constants.STATUS_PATH_URI_ANDROID
        val isPermissionGranted = folderPermissionHandler.isPermissionGranted(uri)
        Log.d(TAG, "PERMISSION: isPermissionGranted = $isPermissionGranted 1")
        if (isPermissionGranted) {
            permissionGranted()
        } else {
            setUpPermissionRequest()
        }


        // Open Settings Activity
        binding.settingsIcon.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        // Bottom Navigation
        bottomNavigation()
    }

    private fun permissionGranted() {
        binding.apply {
            // make views visible
            appBarLayout.visibility = View.VISIBLE
            frameLayout.visibility = View.VISIBLE
            bottomNavigation.visibility = View.VISIBLE

            // hide permission holder
            permissionHolder.visibility = View.GONE
        }
    }

    private fun setUpPermissionRequest() {
        folderPermissionHandler.initFolderPickerLauncher { uri ->
            if (uri != null) {
                // Permission granted and folder selected
                Log.d(TAG, "PERMISSION: Permission granted. Folder selected: $uri")
                SharedPreferenceUtils.putPrefBoolean(
                    SharedPrefKeys.PREF_KEY_STATUS_PERMISSION_GRANTED, true
                )
                permissionGranted()
            } else {
                // Permission not granted or selection canceled
                Log.d(TAG, "PERMISSION: Permission denied.")
                Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        if (!folderPermissionHandler.isPermissionGranted(uri)) {
            Log.d(TAG, "PERMISSION: folderPermissionHandler.isPermissionGranted(uri) = ${folderPermissionHandler.isPermissionGranted(uri)} 2")
            Log.d(TAG, "PERMISSION: !folderPermissionHandler.isPermissionGranted(uri) = ${!folderPermissionHandler.isPermissionGranted(uri)} 2")
            binding.givePermissionAccess.allowPermission.setOnClickListener {
                folderPermissionHandler.requestFolderPermission(uri)
            }
        } else {
            Log.d(TAG, "PERMISSION: !folderPermissionHandler.isPermissionGranted(uri) = ${!folderPermissionHandler.isPermissionGranted(uri)}")
            permissionGranted()
        }
    }

    private fun bottomNavigation() {
        binding.apply {
            loadFragment(WhatsappFragment())
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.whatsapp -> {
                        loadFragment(WhatsappFragment())
                    }
                    R.id.w_business -> {
                        loadFragment(WBusinessFragment())
                    }
                    R.id.downloaded -> {
                        loadFragment(SavedFragment())
                        }
                }
                return@setOnItemSelectedListener true
            }
        }
    }
}
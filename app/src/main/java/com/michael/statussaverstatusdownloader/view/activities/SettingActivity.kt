package com.michael.statussaverstatusdownloader.view.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.michael.statussaverstatusdownloader.R
import com.michael.statussaverstatusdownloader.databinding.ActivitySettingBinding
import com.michael.statussaverstatusdownloader.utils.Constants
import com.michael.statussaverstatusdownloader.utils.SharedPrefKeys
import com.michael.statussaverstatusdownloader.utils.SharedPreferenceUtils
import com.michael.statussaverstatusdownloader.utils.ThemeUtils
import com.michael.statussaverstatusdownloader.utils.ThemeUtils.applyTheme
import com.michael.statussaverstatusdownloader.utils.ThemeUtils.saveTheme

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private var activity: Activity = this@SettingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Apply theme
        setTheme()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }


    private fun setTheme() {
        binding.apply {
            val themes = arrayOf("System Default", "Light", "Dark")
            var currentTheme = ThemeUtils.getTheme()

            val selectedThemeText = when (currentTheme) {
                Constants.SYSTEM_DEFAULT_THEME -> "System Default"
                Constants.LIGHT_THEME -> "Light"
                Constants.DARK_THEME -> "Dark"
                else -> "System Default"
            }
            displaySelectedTheme.text = selectedThemeText

            // Set click listeners for theme options and show the dialog
            themeLayout.setOnClickListener {
                MaterialAlertDialogBuilder(activity)
                    .setTitle("Choose Theme")
                    .setSingleChoiceItems(themes, currentTheme) { _, which ->
                        currentTheme = which // Update the current theme based on selection
                    }
                    .setPositiveButton("OK") { dialog, _ ->
                        saveTheme(currentTheme) // Save the selected theme
                        applyTheme(currentTheme) // Apply the selected theme
                        dialog.dismiss()

                        // Restart the activity to apply the new theme
                        activity.recreate()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }


}
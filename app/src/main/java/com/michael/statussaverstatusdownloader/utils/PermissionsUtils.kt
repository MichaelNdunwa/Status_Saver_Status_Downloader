package com.michael.statussaverstatusdownloader.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class FolderPermissionHandler(
    private val activity: AppCompatActivity
) {
    private lateinit var folderPickerLauncher: ActivityResultLauncher<Intent>
    private val TAG = "FolderPermissionHandler"

    // Initialize the launcher early in the lifecycle
    fun initFolderPickerLauncher(onFolderPicked: (Uri?) -> Unit) {
        folderPickerLauncher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedUri = result.data?.data
                if (selectedUri != null) {
                    // Persist folder permissions
                    activity.contentResolver.takePersistableUriPermission(
                        selectedUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                onFolderPicked(selectedUri)
            } else {
                onFolderPicked(null)
            }
        }
    }

    // Trigger the folder picker for the given URI
    fun requestFolderPermission(initialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
            putExtra("android.content.extra.SHOW_ADVANCED", true)
            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }
        folderPickerLauncher.launch(intent)
    }

    // Check if the permission for a specific URI is already granted
    fun isPermissionGranted(uri: Uri): Boolean {
        try {
            val persistedUri = activity.contentResolver.persistedUriPermissions
            Log.d(TAG, "Checking permission for URI: $uri")

            // Convert document URI  to tree URI if necessary
            val treeUri = if (uri.path?.contains("/document/") == true) {
                val treeUriString = uri.toString().replace("/document/", "/tree/")
                Uri.parse(treeUriString)
            } else {
                uri
            }

            // Check for matching persisted permission
            val hasPersistedPermission = persistedUri.any { persistedUri ->
                val matches = persistedUri.uri == treeUri
                val hasRequiredPermission = persistedUri.isReadPermission && persistedUri.isWritePermission

                Log.d(TAG, """
                    Persisted URI: ${persistedUri.uri}
                    Requested tree URI: $treeUri
                    Matches requested URI: $matches
                    Has read permission: ${persistedUri.isReadPermission}
                    Has read permission: ${persistedUri.isReadPermission}
                    Has write permission: ${persistedUri.isWritePermission}
                """.trimIndent())
                matches && hasRequiredPermission
            }

            if (hasPersistedPermission) {
                Log.d(TAG, "Found valid persisted permission for URI")
                return true
            }

            // If no permission is found return false instead of trying to take permission
            // This avoids the SecurityException we were seeing
            Log.d(TAG, "No valid persisted permission found")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "Error checking URI permission", e)
            return false
        }
    }

}

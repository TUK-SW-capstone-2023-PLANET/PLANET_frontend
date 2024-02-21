package com.example.planet.util

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.example.planet.BuildConfig
import com.example.planet.R
import com.example.planet.TAG
import java.io.File

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            // 1
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            // 2
            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )
            // 3
            val authority = BuildConfig.APPLICATION_ID + ".provider"
            // 4
            Log.d(TAG, "directory: $directory\nfile: $file\n authority: $authority")
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}
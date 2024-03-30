package com.francescapavone.menuapp.utils

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
//import androidx.appcompat.app.AppCompatActivity

import java.io.File

class DownloadHelper() /*: AppCompatActivity()*/ {

    fun download(url: String, fileName: String, downloadManager: DownloadManager) {
        val fileLink = Uri.parse(url)
        val request = DownloadManager.Request(fileLink)

        try {
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("application/pdf")
                .setAllowedOverRoaming(false)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("$fileName.pdf")
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    File.separator + fileName + ".pdf"
                )

            downloadManager.enqueue(request)
            //Fare pop up che esce e dice "PDF is Downloaded"
        } catch (e: Exception) {
            //Fare pop up che esce e dice "Download Failed"
        }
    }

}
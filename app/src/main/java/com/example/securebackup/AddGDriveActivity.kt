package com.example.securebackup

import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_gdrive.*
import java.io.File

class AddGDriveActivity : AppCompatActivity(), ServiceListener {

    enum class ButtonState {
        LOGGED_OUT,
        LOGGED_IN
    }

    private lateinit var googleDriveService: GoogleDriveService
    private var state = ButtonState.LOGGED_OUT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gdrive)

        //1
        val config = GoogleDriveConfig(
            getString(R.string.source_google_drive),
            GoogleDriveService.documentMimeTypes
        )
        googleDriveService = GoogleDriveService(this, config)

        //2
        googleDriveService.serviceListener = this

        //3
        googleDriveService.checkLoginStatus()

        //4
        login.setOnClickListener {
            googleDriveService.auth()
        }
        start.setOnClickListener {
            googleDriveService.pickFiles(null)
        }
        logout.setOnClickListener {
            googleDriveService.logout()
            state = ButtonState.LOGGED_OUT
            setButtons()
        }

        //5
        setButtons()
    }
    override fun loggedIn() {
        state = ButtonState.LOGGED_IN
        setButtons()
    }

    override fun fileDownloaded(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val apkURI = FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".provider",
            file)
        val uri = Uri.fromFile(file)
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        intent.setDataAndType(apkURI, mimeType)
        intent.flags = FLAG_GRANT_READ_URI_PERMISSION
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), R.string.not_open_file, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun cancelled() {
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.status_user_cancelled, Snackbar.LENGTH_LONG).show()
    }

    override fun handleError(exception: Exception) {
        val errorMessage = getString(R.string.status_error, exception.message)
        Snackbar.make(getWindow().getDecorView().getRootView(), errorMessage, Snackbar.LENGTH_LONG).show()
    }


    private fun setButtons() {
        when (state) {
            ButtonState.LOGGED_OUT -> {
                status.text = getString(R.string.status_logged_out)
                start.isEnabled = false
                logout.isEnabled = false
                login.isEnabled = true
            }

            else -> {
                status.text = getString(R.string.status_logged_in)
                start.isEnabled = true
                logout.isEnabled = true
                login.isEnabled = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);  //aggiunto io
        googleDriveService.onActivityResult(requestCode, resultCode, data)
    }

}

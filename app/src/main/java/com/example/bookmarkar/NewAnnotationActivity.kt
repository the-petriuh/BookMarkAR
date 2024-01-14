package com.example.bookmarkar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.bookmarkar.camera.CameraHelper
import com.example.bookmarkar.databinding.ActivityNewAnnotationBinding
import com.google.mlkit.vision.text.Text
import com.example.bookmarkar.MainActivity.Companion.TAG

class NewAnnotationActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityNewAnnotationBinding
    private lateinit var cameraHelper: CameraHelper

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewAnnotationBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        cameraHelper = CameraHelper(
            owner = this,
            context = this.applicationContext,
            viewFinder = viewBinding.pvCameraView,
            onDetectedTextUpdated = ::onDetectedTextUpdated
        )

        cameraHelper.start()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun onDetectedTextUpdated(visionText: Text) {
        Log.d(TAG, "detected text: $visionText")
    }

    override fun onDestroy() {
        cameraHelper.stop()
        super.onDestroy()
    }

}
    
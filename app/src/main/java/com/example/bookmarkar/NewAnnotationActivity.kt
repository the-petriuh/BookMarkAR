package com.example.bookmarkar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.bookmarkar.camera.CameraHelper
import com.example.bookmarkar.databinding.ActivityNewAnnotationBinding
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

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
            viewFinder = viewBinding.pvCameraView
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

    override fun onDestroy() {
        cameraHelper.stop()
        super.onDestroy()
    }

}
    
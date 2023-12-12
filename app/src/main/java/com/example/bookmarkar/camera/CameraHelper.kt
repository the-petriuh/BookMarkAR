package com.example.bookmarkar.camera

import android.Manifest
import android.view.WindowMetrics
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import com.example.bookmarkar.MainActivity.Companion.TAG

class CameraHelper(
    private val owner: AppCompatActivity,
    private val context: Context,
    private val viewFinder: PreviewView
) {

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    @RequiresApi(Build.VERSION_CODES.R)
    val metrics: WindowMetrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics

    @RequiresApi(Build.VERSION_CODES.R)
    fun start() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(owner, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    fun stop() {
        cameraExecutor.shutdown()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("back and front camera unavailable")
            }

            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(context))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider ?: throw IllegalStateException("Camera initialization failed")

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        val previewView = getPreviewUseCase()

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                owner,
                cameraSelector,
                previewView
            )

            previewView.setSurfaceProvider(viewFinder.surfaceProvider)
        } catch (ex: Exception) {
            Log.e(TAG, "Use case binding failed $ex")
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun aspectRatio(): Int {
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val previewRatio = max(width, height).toDouble() / min(width, height)

        if(abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun hasBackCamera() =
        cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false

    private fun hasFrontCamera() =
        cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getPreviewUseCase(): Preview {
        return Preview.Builder()
            .setTargetAspectRatio(aspectRatio())
            .setTargetRotation(viewFinder.display.rotation)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    context, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 42
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }
}
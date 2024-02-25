package com.example.bookmarkar
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.bookmarkar.camera.CameraHelper
import com.example.bookmarkar.databinding.ActivityNewAnnotationBinding
import com.google.mlkit.vision.text.Text
import com.example.bookmarkar.MainActivity.Companion.TAG
import kotlin.math.log

class NewAnnotationActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityNewAnnotationBinding
    private lateinit var cameraHelper: CameraHelper
    private var textRects: MutableList<Rect> = mutableListOf()

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
        textRects.clear()
        var adjustedRect: Rect? = null

        for (block in visionText.textBlocks) {
            for (line in block.lines) {
                adjustedRect = adjustPosition(line.boundingBox!!)
                if (viewBinding.cvHighlighterArea.isInsideOfHighlighterArea(adjustedRect) == true) {
                    textRects.add(adjustedRect)
                }

            }
        }

        viewBinding.cvTextHighlighter.setTargets(textRects.toList())
    }

    private fun adjustPosition(rect: Rect): Rect {
        return Rect(
            (rect.left * 0.85).toInt(),
            (rect.top * 2.4).toInt(),
            (rect.right * 2.15).toInt(),
            (rect.bottom * 2.4).toInt()
        )
    }

    override fun onDestroy() {
        cameraHelper.stop()
        super.onDestroy()
    }

}
    
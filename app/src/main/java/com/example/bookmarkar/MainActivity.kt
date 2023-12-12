package com.example.bookmarkar

import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmarkar.adapter.AnnotationListAdapter
import com.example.bookmarkar.viewmodel.AnnotationViewModel
import com.example.bookmarkar.viewmodel.AnnotationViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val annotationViewModel: AnnotationViewModel by viewModels {
        AnnotationViewModelFactory((application as BookMarksApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        val annotationListAdapter = AnnotationListAdapter()
        recyclerView.adapter = annotationListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        annotationViewModel.allAnnotations.observe(this) {annotations ->
            annotations.let { annotationListAdapter.submitList(it) }
        }

        // set fab button to call camera to add new annotation
        val btnFab = findViewById<FloatingActionButton>(R.id.fab)
        btnFab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewAnnotationActivity::class.java)
            startActivity(intent)
        }

        getPermissions()
    }

    private fun getPermissions() {
        val permissionList = mutableListOf<String>()

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.CAMERA)

        if (permissionList.size > 0)
            requestPermissions(permissionList.toTypedArray(), 101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                getPermissions()
            }
        }
    }

    companion object {
        const val TAG = "CameraXDemo"
    }
}
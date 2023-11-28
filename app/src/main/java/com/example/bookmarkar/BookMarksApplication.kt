package com.example.bookmarkar

import android.app.Application
import com.example.bookmarkar.database.AnnotationDatabase
import com.example.bookmarkar.repo.AnnotationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BookMarksApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AnnotationDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { AnnotationRepository(database.annotationDao()) }
}
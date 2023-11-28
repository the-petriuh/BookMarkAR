package com.example.bookmarkar.repo

import androidx.annotation.WorkerThread
import com.example.bookmarkar.database.AnnotationDao
import com.example.bookmarkar.model.Annotation
import kotlinx.coroutines.flow.Flow

class AnnotationRepository(private val annotationDao: AnnotationDao) {

    val allAnnotations: Flow<List<Annotation>> = annotationDao.getAllAnnotations()

    @WorkerThread
    suspend fun insert(annotation: Annotation) {
        annotationDao.insertAnnotation(annotation)
    }
}
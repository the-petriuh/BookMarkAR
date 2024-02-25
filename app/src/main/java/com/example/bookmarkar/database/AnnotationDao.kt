package com.example.bookmarkar.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookmarkar.model.Annotation
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnotationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnnotation(annotation: Annotation)

    @Query("DELETE FROM Annotation")
    suspend fun deleteAllAnnotation()

    @Query("SELECT * FROM Annotation ORDER BY annotationId ASC")
    fun getAllAnnotations(): Flow<List<Annotation>>

    @Query("SELECT * FROM Annotation WHERE annotationId = :annotationID")
    fun getAnnotationById(annotationID: Int) : Annotation
}
package com.example.bookmarkar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import java.time.LocalDateTime
//import java.util.*

@Entity()
data class Annotation(
    @ColumnInfo(name = "book_name") val bookName: String,
    @ColumnInfo(name = "book_annotation") val bookAnnotation: String,
    @ColumnInfo(name = "page_number") val pageNumber: Int,
    @PrimaryKey(autoGenerate = true) val annotationId: Int? = null
)

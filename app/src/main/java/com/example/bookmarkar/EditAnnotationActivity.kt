package com.example.bookmarkar

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bookmarkar.repo.AnnotationRepository
import com.example.bookmarkar.model.Annotation


class EditAnnotationActivity : AppCompatActivity() {

//    private val bookTitleView: TextView = findViewById(R.id.txed_book_title)
//    private val bookAnnotationView: TextView = findViewById(R.id.txed_book_annotation)
//    private val bookPageView:  TextView = findViewById(R.id.txed_book_page)
    private lateinit var annotation: Annotation

    private var annotationID: Int = 0

//    private val annotationRepository: AnnotationRepository = (application as BookMarksApplication).repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_annotation)

        annotationID = intent.getIntExtra("annotationId", -1)

//        annotation = annotationRepository.getAnnotationById(annotationID)

//        bookTitleView.text = "Teste"//annotation.bookName
//        bookAnnotationView.text = "Teste"//annotation.bookAnnotation
//        bookPageView.text = "Teste"//annotation.pageNumber.toString()
    }
}
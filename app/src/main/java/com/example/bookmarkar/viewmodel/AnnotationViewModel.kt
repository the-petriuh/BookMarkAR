package com.example.bookmarkar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookmarkar.model.Annotation
import com.example.bookmarkar.repo.AnnotationRepository
import kotlinx.coroutines.launch

class AnnotationViewModel(private val repository: AnnotationRepository) : ViewModel() {
    val allAnnotations: LiveData<List<Annotation>> = repository.allAnnotations.asLiveData()

    fun insert(annotation: Annotation) = viewModelScope.launch {
        repository.insert(annotation)
    }
}

class AnnotationViewModelFactory(private val repository: AnnotationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnotationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnnotationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
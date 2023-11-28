package com.example.bookmarkar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmarkar.R
import com.example.bookmarkar.model.Annotation

class AnnotationListAdapter : ListAdapter<Annotation, AnnotationListAdapter.AnnotationViewHolder>(AnnotationComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnotationViewHolder {
        return AnnotationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AnnotationViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class AnnotationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookNameView: TextView = itemView.findViewById(R.id.txv_book_name)
        private val bookAnnotationContentView: TextView = itemView.findViewById(R.id.txv_book_annotation_content)
        private val bookPageView: TextView = itemView.findViewById(R.id.txv_book_page)

        fun bind(currentAnnotation: Annotation) {
            bookNameView.text = currentAnnotation.bookName
            bookAnnotationContentView.text = currentAnnotation.bookAnnotation
            bookPageView.text = currentAnnotation.pageNumber.toString()
        }

        companion object {
            fun create(parent: ViewGroup): AnnotationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.annotation_item, parent, false)
                return AnnotationViewHolder(view)
            }
        }
    }

    class AnnotationComparator : DiffUtil.ItemCallback<Annotation>() {
        override fun areItemsTheSame(oldItem: Annotation, newItem: Annotation): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Annotation, newItem: Annotation): Boolean {
            return oldItem.bookName == newItem.bookName &&
                    oldItem.bookAnnotation == newItem.bookAnnotation &&
                    oldItem.pageNumber == newItem.pageNumber
        }
    }
}
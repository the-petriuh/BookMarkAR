package com.example.bookmarkar.text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class TextHighlighterView : View {
    private var paint: Paint? = null
    private val targets: MutableList<Rect> = ArrayList()

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    init {
        paint = Paint()
        paint!!.color = Color.parseColor("#FFEA00") // Set the color to red with full opacity
        paint!!.style = Paint.Style.FILL
        paint!!.alpha = 60 // Set the alpha value for transparency (0-255)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw a transparent rectangle at position (left, top, right, bottom)
        synchronized(this) {
            for (entry in targets) {
                canvas.drawRect(entry, paint!!)
            }
        }
    }

    public fun setTargets(sources: List<Rect>) {
        synchronized(this) {
            targets.clear()
            targets.addAll(sources)
            this.postInvalidate()
        }
    }
}
package com.example.bookmarkar.texthighlighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class TextHighlighterView : View {
    private var paint: Paint? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        paint = Paint()
        paint!!.color = Color.parseColor("#FFEA00") // Set the color to red with full opacity
        paint!!.style = Paint.Style.FILL
        paint!!.alpha = 60 // Set the alpha value for transparency (0-255)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw a transparent rectangle at position (left, top, right, bottom)
        canvas.drawRect(100f, 100f, 300f, 300f, paint!!)
    }
}
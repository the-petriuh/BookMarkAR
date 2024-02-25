package com.example.bookmarkar.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.bookmarkar.MainActivity.Companion.TAG

class HighlighterAreaView : View {
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var endX: Float = 0f
    private var endY: Float = 0f
    private var isDrawing = false
    private var isUpEvent = false
    private var paint: Paint? = null
    var highlighterAreaRect: Rect? = null
    private var flag: String = ""

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
        paint!!.color = Color.parseColor("#FFFFFF") // Set the color to red with full opacity
        paint!!.style = Paint.Style.FILL
        paint!!.alpha = 40 // Set the alpha value for transparency (0-255)
    }

    override fun onDraw(canvas: Canvas) {
        Log.d(TAG, flag)
        super.onDraw(canvas)
        if (isDrawing || isUpEvent) {
            canvas.drawRect(highlighterAreaRect!!, paint!!)
            isUpEvent = false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                endX = event.x
                endY = event.y
                isDrawing = true
                setHighlighterAreaRect()
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                endX = event.x
                endY = event.y
                setHighlighterAreaRect()
                invalidate()
                flag = "move event - isDrawing: $isDrawing"
            }
            MotionEvent.ACTION_UP -> {
                endX = event.x
                endY = event.y
                isDrawing = false
                isUpEvent = true
                flag = "up event - isDrawing: $isDrawing"
//                invalidate()
            }
        }
        return true
    }

    private fun setHighlighterAreaRect() {
        if (highlighterAreaRect == null) {
            highlighterAreaRect = Rect(
                startX.toInt().coerceAtMost(endX.toInt()),
                startY.toInt().coerceAtMost(endY.toInt()),
                startX.toInt().coerceAtLeast(endX.toInt()),
                startY.toInt().coerceAtLeast(endY.toInt())
            )
        } else {
            highlighterAreaRect!!.set(
                startX.toInt().coerceAtMost(endX.toInt()),
                startY.toInt().coerceAtMost(endY.toInt()),
                startX.toInt().coerceAtLeast(endX.toInt()),
                startY.toInt().coerceAtLeast(endY.toInt())
            )
        }
    }

    public fun isInsideOfHighlighterArea(rect: Rect): Boolean? = highlighterAreaRect?.contains(rect)

}

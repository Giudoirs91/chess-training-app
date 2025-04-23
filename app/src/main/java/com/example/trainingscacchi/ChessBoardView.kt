package com.example.trainingscacchi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class ChessBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val boardSize = 8
    private var cellSize = 0f

    // Colori della scacchiera
    private val lightColor = Color.rgb(240, 217, 181) // beige chiaro
    private val darkColor = Color.rgb(181, 136, 99)   // marrone

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Calcola la dimensione di ogni cella
        cellSize = (minOf(w, h) / boardSize).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Disegna la scacchiera
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                val isLightSquare = (row + col) % 2 == 0
                paint.color = if (isLightSquare) lightColor else darkColor

                val left = col * cellSize
                val top = row * cellSize
                val right = left + cellSize
                val bottom = top + cellSize

                canvas.drawRect(left, top, right, bottom, paint)
            }
        }
    }
}
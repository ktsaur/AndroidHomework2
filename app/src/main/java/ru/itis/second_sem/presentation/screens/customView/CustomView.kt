package ru.itis.second_sem.presentation.screens.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min
import kotlin.math.sqrt

class CustomView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0,
): View(ctx, attrs, defStyleAttrs) {

    private val sectors = mutableListOf<Float>()
    private val sectorColors = mutableListOf<Int>()
    private var selectedSector: Int = -1

    private val ringPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textSize = 60f
        textAlign = Paint.Align.LEFT
        color = Color.BLACK
    }

    private val bounds = RectF()
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f
    private val strokeWidth = 40f
    private val gapBetweenRings = 10f

    fun setSectorsData(values: List<Float>, colors: List<Int>) {
        sectors.clear()
        sectors.addAll(values)
        sectorColors.clear()
        sectorColors.addAll(colors)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2f
        centerY = h / 2f
        radius = (min(w, h) / 2f) - strokeWidth
        bounds.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (sectors.isEmpty()) return

        ringPaint.strokeWidth = strokeWidth

        sectors.forEachIndexed { index, progress ->
            val currentRadius = radius - (index * (strokeWidth + gapBetweenRings))
            bounds.set(
                centerX - currentRadius,
                centerY - currentRadius,
                centerX + currentRadius,
                centerY + currentRadius
            )

            ringPaint.color = Color.LTGRAY
            canvas.drawArc(bounds, 0f, 360f, false, ringPaint)

            val sectorColor = sectorColors[index]
            ringPaint.color = if (index == selectedSector) {
                Color.argb(
                    255,
                    minOf(255, (Color.red(sectorColor) * 1.5).toInt()),
                    minOf(255, (Color.green(sectorColor) * 1.5).toInt()),
                    minOf(255, (Color.blue(sectorColor) * 1.5).toInt())
                )
            } else {
                sectorColor
            }

            val sweepAngle = (progress / 100f) * 360f
            canvas.drawArc(bounds, 90f, sweepAngle, false, ringPaint)

            if (index == selectedSector) {
                val text = "${progress.toInt()}%"
                textPaint.color = sectorColor
                textPaint.isFakeBoldText = true

                val textX = centerX + radius + strokeWidth
                val textY = centerY
                canvas.drawText(text, textX, textY, textPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val touchX = event.x - centerX
                val touchY = event.y - centerY

                val touchRadius = sqrt(touchX * touchX + touchY * touchY)

                sectors.indices.forEach { index ->
                    val currentRadius = radius - (index * (strokeWidth + gapBetweenRings))
                    val minRadius = currentRadius - strokeWidth / 2
                    val maxRadius = currentRadius + strokeWidth / 2

                    if (touchRadius in minRadius..maxRadius) {
                        selectedSector = if (selectedSector == index) -1 else index
                        invalidate()
                        performClick()
                        return true
                    }
                }
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
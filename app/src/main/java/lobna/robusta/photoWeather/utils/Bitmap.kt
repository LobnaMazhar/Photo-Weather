package lobna.robusta.photoWeather.utils

import android.graphics.*

/**
 * Draw given text on top of a bitmap
 *
 * @param text to be drawn on the bitmap
 * @param textSize optional size of text, default is set to 40f
 * @param color optional color for the text, default is set to black
 * */
fun Bitmap.drawText(
    text: String,
    textSize: Float = 55f,
    color: Int = Color.BLACK
): Bitmap {
    val bitmap = copy(config, true)
    val canvas = Canvas(bitmap)

    Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        this.color = color
        this.textSize = textSize
        typeface = Typeface.SERIF
        setShadowLayer(1f, 0f, 1f, Color.WHITE)

        val bounds = Rect()
        getTextBounds(text, 0, text.length, bounds)

        canvas.drawText(text, 20f, bounds.height() + 20f, this)
    }

    return bitmap
}

/**
 * Rotate a bitmap with the given degress
 *
 * @param degrees rotation degrees for the bitmap to be rotated by
 * */
fun Bitmap.rotateBitmap(degrees: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

package com.lf.common.ui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.ui.view$
 * @ClassName:      ImageViewExts$
 * @Author:         LF
 * @CreateDate:     2021/8/5$ 17:06$
 * @Description:
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

//圆形
fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).transform(CenterCrop()).into(this)
}

//glide的图片裁剪和imageView scaleType有冲突
//解决办法 transform(CenterCrop(), RoundedCorners(corner)) 先裁剪在设置圆角
//圆角
fun ImageView.loadCorner(url: String, corner: Int) {
    Glide.with(this).load(url).transform(CenterCrop(), RoundedCorners(corner)).into(this)
}

//圆角描边
fun ImageView.loadCircleBorder(
    url: String,
    borderWidth: Float = 0f,
    borderColor: Int = Color.WHITE
) {
    Glide.with(this).load(url).transform(CircleBorderTransform(borderWidth, borderColor)).into(this)
}

class CircleBorderTransform(val borderWidth: Float, val borderColor: Int) : CircleCrop() {
    private var borderPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        borderPaint.setColor(borderColor)
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val halfWidth = outWidth / 2.toFloat()
        val halfHeight = outHeight / 2.toFloat()
        canvas.drawCircle(
            halfWidth,
            halfHeight,
            Math.min(halfWidth, halfHeight) - borderWidth / 2,
            borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }

}

package com.lf.hi.ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.lf.hi.hilibrary.util.HiDisplayUtil
import com.lf.hi.ui.R

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.ui.banner.indicator$
 * @ClassName:      HiCircleIndicator$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 16:50$
 * @Description:圆形指示器
 */
class HiCircleIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), HiIndicator<FrameLayout> {


    companion object{
        private const val VWM = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    //正常状态下的指示点
    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    //选中状态下的指示点
    @DrawableRes
    private val mPointSelect = R.drawable.shape_point_select

    //指示点左右内间距
    private var mPointLeftRightPadding = 0

    //指示点上下内间距
    private var mPointTopBottomPadding = 0

    init {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(getContext(), 5f)
        mPointTopBottomPadding = HiDisplayUtil.dp2px(getContext(), 15f)
    }


    override fun get(): FrameLayout {
       return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }
        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL
        var imageView: ImageView
        val imageViewParams  = LinearLayout.LayoutParams(VWM, VWM)
        imageViewParams.gravity = Gravity.CENTER_VERTICAL
        imageViewParams.setMargins(
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding
        )
        for (i in 0 until count) {
            imageView = ImageView(context)
            imageView.layoutParams = imageViewParams
            if (i == 0) {
                imageView.setImageResource(mPointSelect)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            groupView.addView(imageView)
        }
        val groupViewParams = LayoutParams(VWM, VWM)
        groupViewParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, groupViewParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView =
                viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(mPointSelect)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }
}
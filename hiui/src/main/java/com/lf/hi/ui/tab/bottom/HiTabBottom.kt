package com.lf.hi.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.lf.hi.ui.R
import com.lf.hi.ui.tab.common.IHiTab

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.ui.tab.bottom$
 * @ClassName:      HiTabBottom$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 17:11$
 * @Description:
 */
class HiTabBottom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context,attrs,defStyleAttr), IHiTab<HiTabBottomInfo<*>> {
    private var tabInfo: HiTabBottomInfo<*>? = null
    private var tabImageView: ImageView? = null
    private var tabIconView: TextView? = null
    private var tabNameView: TextView? = null

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tab_bottom, this)
        tabImageView = findViewById<ImageView>(R.id.iv_image)
        tabIconView = findViewById<TextView>(R.id.tv_icon)
        tabNameView = findViewById<TextView>(R.id.tv_name)
    }

    fun getHiTabInfo(): HiTabBottomInfo<*>? {
        return tabInfo
    }

    fun getTabImageView(): ImageView? {
        return tabImageView
    }

    fun getTabIconView(): TextView? {
        return tabIconView
    }

    fun getTabNameView(): TextView? {
        return tabNameView
    }


    override fun OnTabSelectedChange(
        index: Int,
        prevInfo: HiTabBottomInfo<*>?,
        nextInfo: HiTabBottomInfo<*>?
    ) {
        if (prevInfo !== tabInfo && nextInfo !== tabInfo || prevInfo === nextInfo) {
            return
        }
        //tabBottom被反选了
        //tabBottom被反选了
        if (prevInfo === tabInfo) {
            inflateInfo(false, false)
        } else {
            inflateInfo(true, false)
        }
    }

    override fun setHiTabInfo(data: HiTabBottomInfo<*>?) {
        tabInfo = data
        inflateInfo(false, true)
    }

    override fun resetHeight(height: Int) {
        val layoutParams = layoutParams
        layoutParams.height = height
        setLayoutParams(layoutParams)
        getTabNameView()!!.visibility = View.GONE
    }

    /**
     *
     * @param selected 是否被选中
     * @param init 是不是初始化
     */
    private fun inflateInfo(selected: Boolean, init: Boolean) {
        if (tabInfo!!.tabType == HiTabBottomInfo.TabType.ICON) { //iconFont
            if (init) {
                tabImageView!!.visibility = View.GONE
                tabIconView!!.visibility = View.VISIBLE
                //加载字体
                val typeface =
                    Typeface.createFromAsset(context.assets, tabInfo!!.iconFont)
                tabIconView!!.setTypeface(typeface)
                if (!TextUtils.isEmpty(tabInfo!!.name)) {
                    tabNameView!!.text = tabInfo!!.name
                }
            }
            if (selected) { //被选中时
                tabIconView!!.text =
                    if (TextUtils.isEmpty(tabInfo!!.selectIconName)) tabInfo!!.defaultIconName else tabInfo!!.selectIconName
                tabIconView!!.setTextColor(getTextColor(tabInfo!!.tintColor))
                tabNameView!!.setTextColor(getTextColor(tabInfo!!.tintColor))
            } else { //未选中时
                tabIconView!!.text = tabInfo!!.defaultIconName
                tabIconView!!.setTextColor(getTextColor(tabInfo!!.defaultColor))
                tabNameView!!.setTextColor(getTextColor(tabInfo!!.defaultColor))
            }
        } else if (tabInfo!!.tabType == HiTabBottomInfo.TabType.BITMAP) {
            if (init) {
                tabImageView!!.visibility = View.VISIBLE
                tabIconView!!.visibility = View.GONE
                if (!TextUtils.isEmpty(tabInfo!!.name)) {
                    tabNameView!!.text = tabInfo!!.name
                }
            }
            if (selected) {
                tabImageView!!.setImageBitmap(tabInfo!!.selectBitmap)
            } else {
                tabImageView!!.setImageBitmap(tabInfo!!.defaultBitmap)
            }
        }
    }

    @ColorInt
    private fun getTextColor(color: Any): Int {
        return if (color is String) {
            Color.parseColor(color)
        } else {
            color as Int
        }
    }
}
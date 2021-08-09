package com.lf.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.ui.view$
 * @ClassName:      IconFontTextView$
 * @Author:         LF
 * @CreateDate:     2021/7/30$ 9:10$
 * @Description:用于支持全局iconfont资源的引用，可以在布局文件中直接设置text
 */
class IconFontTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context!!, attrs, defStyleAttr) {
    init {
        val iconfont = Typeface.createFromAsset(
            getContext().assets,
            "fonts/iconfont.ttf"
        )
        this.typeface = iconfont
    }
}
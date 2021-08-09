package com.lf.common.ui.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.lf.common.R

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.ui.view$
 * @ClassName:      EmptyView$
 * @Author:         LF
 * @CreateDate:     2021/8/3$ 16:25$
 * @Description:
 */
open class EmptyView : LinearLayout {
    private val title: TextView
    private val icon: TextView
    private val desc: TextView
    private val tips: TextView
    private val action: Button

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)

        icon = findViewById(R.id.empty_icon)
        title = findViewById(R.id.empty_title)
        desc = findViewById(R.id.empty_text)
        tips = findViewById(R.id.empty_tips)
        action = findViewById(R.id.empty_action)
    }

    fun setIcon(@StringRes iconRes: Int) {
        icon.setText(iconRes)
    }

    fun setTitle(text:String) {
        title.setText(text)
        title.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE

    }

    fun setDesc(text: String) {
        desc.text = text
        desc.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    @JvmOverloads
    fun setHelpAction(actionId:Int = R.string.if_detail,listener:OnClickListener){
        tips.setText(actionId)
        tips.setOnClickListener(listener)
        tips.visibility = View.VISIBLE
        if (actionId == -1){
            tips.visibility = View.GONE
        }
    }




    fun setButton(text: String,listener: OnClickListener){
        if (TextUtils.isEmpty(text)){
            action.visibility = View.GONE
        }else{
            action.visibility = View.VISIBLE
            action.text = text
            action.setOnClickListener(listener)
        }
    }
}
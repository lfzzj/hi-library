package com.lf.common.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.lf.common.R

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.common.ui.component$
 * @ClassName:      InputItemLayout$
 * @Author:         LF
 * @CreateDate:     2021/7/30$ 9:15$
 * @Description:
 */
open class InputItemLayout : LinearLayout {
    private var topLine: Line
    private var bottomLine: Line
    private lateinit var editText: TextView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        orientation = LinearLayout.HORIZONTAL

        val array = context.obtainStyledAttributes(attributeSet, R.styleable.InputItemLayout)
        val titleStyleId = array.getResourceId(R.styleable.InputItemLayout_titleTextAppearance, 0)
        val title = array.getString(R.styleable.InputItemLayout_title)
        //解析title资源属性
        parseTitleStyle(titleStyleId, title)

        val inputStyleId = array.getResourceId(R.styleable.InputItemLayout_inputTextAppearance, 0)
        val hint = array.getString(R.styleable.InputItemLayout_hint)

        val inputType = array.getInteger(R.styleable.InputItemLayout_inputType, 0)
        //解析右侧输入资源属性
        parseInputTypeStyle(inputStyleId, hint, inputType)

        //解析上下分割线配置属性
        val topLineStyleId = array.getResourceId(R.styleable.InputItemLayout_topLineAppearance, 0)
        val bottomLineStyleId =
            array.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance, 0)
        topLine = parseLineStyle(topLineStyleId)
        bottomLine = parseLineStyle(bottomLineStyleId)

        if (topLine.enable) {
            topPaint.setColor(topLine.color)
            topPaint.style = Paint.Style.FILL_AND_STROKE
            topPaint.strokeWidth = topLine.height.toFloat()
        }

        if (bottomLine.enable) {
            bottomPaint.setColor(bottomLine.color)
            bottomPaint.style = Paint.Style.FILL_AND_STROKE
            bottomPaint.strokeWidth = bottomLine.height.toFloat()
        }

        array.recycle()
    }

    private fun parseLineStyle(resId: Int): Line {
        val line = Line()
        val array = context.obtainStyledAttributes(resId, R.styleable.lineAppearance)
        line.color =
            array.getColor(R.styleable.lineAppearance_color, resources.getColor(R.color.color_d1d2))
        line.height = array.getDimensionPixelOffset(R.styleable.lineAppearance_height, 0)
        line.leftMargin = array.getDimensionPixelOffset(R.styleable.lineAppearance_leftMargin, 0)
        line.rightMargin = array.getDimensionPixelOffset(R.styleable.lineAppearance_rightMargin, 0)
        line.enable = array.getBoolean(R.styleable.lineAppearance_enable, false)
        array.recycle()
        return line
    }

    inner class Line {
        var color = 0
        var height = 0
        var leftMargin = 0
        var rightMargin = 0
        var enable = false
    }

    private fun parseTitleStyle(resId: Int, title: String?) {
        val array = context.obtainStyledAttributes(resId, R.styleable.titleTextAppearance)
        val titleColor = array.getColor(
            R.styleable.titleTextAppearance_titleColor,
            resources.getColor(R.color.color_565)
        )
        val titleSize = array.getDimensionPixelOffset(
            R.styleable.titleTextAppearance_textSize,
            applyUnit(TypedValue.COMPLEX_UNIT_SP, 15f)
        )
        val minWidth = array.getDimensionPixelOffset(
            R.styleable.titleTextAppearance_minWidth,
            applyUnit(TypedValue.COMPLEX_UNIT_SP, 0f)
        )
        val textView = TextView(context)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize.toFloat())
        textView.setTextColor(titleColor)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        textView.layoutParams = params
        textView.minWidth = minWidth
        textView.gravity = Gravity.CENTER

        textView.text = title
        addView(textView)
        array.recycle()
    }

    private fun parseInputTypeStyle(resId: Int, hint: String?, inputType: Int) {
        val array = context.obtainStyledAttributes(resId, R.styleable.inputTextAppearance)
        val hintColor = array.getColor(
            R.styleable.inputTextAppearance_hintColor,
            resources.getColor(R.color.color_d1d2)
        )
        val inputColor = array.getColor(
            R.styleable.inputTextAppearance_inputColor,
            resources.getColor(R.color.color_565)
        )
        //px
        val textSize =
            array.getDimensionPixelOffset(
                R.styleable.inputTextAppearance_hintSize,
                applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)
            )
        editText = EditText(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        editText.layoutParams = params
        editText.setHint(hint)
        editText.setHintTextColor(hintColor)
        editText.setTextColor(inputColor)
        editText.isFocusableInTouchMode=true
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())

        editText.setBackgroundColor(Color.TRANSPARENT)
        editText.gravity = Gravity.CENTER and  Gravity.LEFT

        if (inputType == 0) {
            editText.inputType = InputType.TYPE_CLASS_TEXT
        } else if (inputType == 1) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or (InputType.TYPE_CLASS_TEXT)
        } else if (inputType == 2) {
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
        addView(editText)
        array.recycle()
    }

    var topPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (topLine.enable) {
            canvas!!.drawLine(
                topLine.leftMargin.toFloat(),
                0f,
                measuredWidth - topLine.rightMargin.toFloat(),
                0f,
                topPaint
            )
        }
        if (bottomLine.enable) {
            canvas!!.drawLine(
                bottomLine.leftMargin.toFloat(),
                height - bottomLine.rightMargin.toFloat(),
                measuredWidth - bottomLine.rightMargin.toFloat(),
                height - bottomLine.rightMargin.toFloat(),
                bottomPaint
            )
        }
    }

    private fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }

    fun getEditText(): TextView {
        return editText
    }

}
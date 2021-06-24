package com.lf.hi.library;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.library$
 * @ClassName: IconFontView$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 9:50$
 * @Description:
 */
public class IconFontView extends androidx.appcompat.widget.AppCompatTextView {
    public IconFontView(Context context) {
        this(context,null);
    }

    public IconFontView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconFontView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface iconfont = Typeface.createFromAsset(getContext().getAssets(), "fonts/iconfont.ttf");
        this.setTypeface(iconfont);

    }
}

package com.lf.hi.ui.banner.core;

import android.content.Context;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.core$
 * @ClassName: HiBannerScroller$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 17:29$
 * @Description:
 */
public class HiBannerScroller extends Scroller {
    //值越大，滚动越慢
    private int mDuration;

    public HiBannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}

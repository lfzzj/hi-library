package com.lf.hi.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lf.hi.hilibrary.util.HiDisplayUtil;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.refresh$
 * @ClassName: HiOverView$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 17:49$
 * @Description:下拉刷新的Overlay视图，可以重载这个类来定义自己的Overlay
 */
public abstract class HiOverView extends FrameLayout {
    public enum HiRefreshState{
        //初始态
        STATE_INIT,
        //Header展示的状态
        STATE_VISIBLE,
        //可刷新中状态
        STATE_REFRESH,
        //超出可刷新距离的状态
        STATE_OVER,
        //超出刷新位置松开手后的状态
        STATE_OVER_RELEASE
    }

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;
    //触发下拉刷新需要的最小高度
    public int mPullRefreshHeight;
    //最小阻尼
    public float minDamp = 1.6f;
    //最大阻尼
    public float maxDamp = 2.0f;
    public HiOverView(@NonNull Context context) {
        super(context);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit(){
        mPullRefreshHeight = HiDisplayUtil.dp2px(getContext(),66);
        init();
    }
    /**
     * 初始化
     */
    public abstract void init();

    /**
     *
     * @param scrollY
     * @param pullRefreshHeight
     */
    protected abstract void onScroll(int scrollY,int pullRefreshHeight);

    /**
     * 显示overlay
     */
    protected abstract void onVisible();

    /**
     * 开始刷新
     */
    public abstract void onRefresh();

    /**
     * 加载完成
     */
    public abstract void onFinish();

    public abstract void onOver();

    public void setState(HiRefreshState state) {
        this.mState = state;
    }

    public HiRefreshState getState() {
        return mState;
    }
}

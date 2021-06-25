package com.lf.hi.ui.banner.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;


/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner$
 * @ClassName: HiViewPager$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 11:09$
 * @Description:实现了自动翻页的viewPager
 */
public class HiViewPager extends ViewPager {

    private int mIntervalTime;
    //是否开启自动轮播
    private boolean mAutoPlay = true;
    //是否有调用过ViewPager的layout方法
    private boolean isLayout;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //切换到下一个
            next();
            mHandler.postDelayed(this, mIntervalTime);
        }
    };

    public HiViewPager(@NonNull Context context) {
        super(context);
    }

    public HiViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否自动播放
     *
     * @param autoPlay
     */
    public void setAutoPlay(boolean autoPlay) {
        if (!autoPlay) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                start();
                break;
            default:
                stop();
                break;
        }
        return super.onTouchEvent(ev);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isLayout & getAdapter() != null && getAdapter().getCount() > 0) {
            try {
                //fix RecyclerView + ViewPager bug
                Field mScroller = ViewPager.class.getDeclaredField("mFirstLayout");
                mScroller.setAccessible(true);
                mScroller.set(this, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        //fix 使用RecyclerView + ViewPager bug
        if (((Activity) getContext()).isFinishing()) {
            super.onDetachedFromWindow();
        }
        stop();
    }

    public void setScrollerDuration(int duration){
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this,new HiBannerScroller(getContext(),duration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIntervalTime(int intervalTime) {
        this.mIntervalTime = intervalTime;
    }

    private void start() {
        mHandler.removeCallbacksAndMessages(null);
        if (mAutoPlay) {
            mHandler.postDelayed(mRunnable, mIntervalTime);
        }
    }


    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 设置下一个要显示的item,并返回item的pos
     */
    private int next() {
        int nextPosition = -1;
        if (getAdapter() == null || getAdapter().getCount() <= 1) {
            stop();
            return nextPosition;
        }
        nextPosition = getCurrentItem() + 1;
        //下一个索引大于adapter的view的最大数量时重新开始
        if (nextPosition >= getAdapter().getCount()) {
            //获取第一个item的索引
            nextPosition = ((HiBannerAdapter) getAdapter()).getFirstItem();
        }
        setCurrentItem(nextPosition, true);
        return nextPosition;
    }

}

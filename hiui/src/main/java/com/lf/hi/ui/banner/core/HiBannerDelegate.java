package com.lf.hi.ui.banner.core;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.lf.hi.ui.R;
import com.lf.hi.ui.banner.HiBanner;
import com.lf.hi.ui.banner.indicator.HiCircleIndicator;
import com.lf.hi.ui.banner.indicator.HiIndicator;

import java.util.List;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.core$
 * @ClassName: HiBannerDelegate$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 14:49$
 * @Description:HiBanner的委托类 HiBanner的控制器
 * 辅助HiBanner完成各种功能的定制
 * 将HiBanner的一些逻辑内聚在这，保证暴露给使用者的HiBanner干净整洁
 */
public class HiBannerDelegate implements IHiBanner, ViewPager.OnPageChangeListener {
    private Context mContext;
    private HiBanner mBanner;
    private HiBannerAdapter mAdapter;
    private HiIndicator<?> mHiIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends HiBannerMo> mHiBannerMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervaltime = 5000;
    private IHiBanner.OnBannerClickListener mOnBannerClickListener;
    private HiViewPager mHiViewPager;
    private int mScrollerDuration = -1;

    public HiBannerDelegate(Context context, HiBanner hiBanner) {
        this.mContext = context;
        this.mBanner = hiBanner;
    }

    @Override
    public void setBannerData(int layoutRes, @NonNull List<? extends HiBannerMo> models) {
        mHiBannerMos = models;
        init(layoutRes);
    }

    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        setBannerData(R.layout.hi_banner_item_image, models);
    }

    @Override
    public void setHiIndicator(HiIndicator hiIndicator) {
        this.mHiIndicator = hiIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mAdapter != null) mAdapter.setAutoPlay(autoPlay);
        if (mHiViewPager != null) mHiViewPager.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervaltime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollerDuration(int duration) {
        this.mScrollerDuration = duration;
        if (mHiViewPager != null && duration > 0) {
            mHiViewPager.setScrollerDuration(duration);
        }

    }

    private void init(int layoutResId) {
        if (mAdapter == null) {
            mAdapter = new HiBannerAdapter(mContext);
        }
        if (mHiIndicator == null) {
            mHiIndicator = new HiCircleIndicator(mContext);
        }
        mHiIndicator.onInflate(mHiBannerMos.size());
        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mHiBannerMos);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        mAdapter.setBannerClickListener(mOnBannerClickListener);

        mHiViewPager = new HiViewPager(mContext);
        mHiViewPager.setIntervalTime(mIntervaltime);
        mHiViewPager.addOnPageChangeListener(this);
        mHiViewPager.setAutoPlay(mAutoPlay);
        mHiViewPager.setAdapter(mAdapter);
        if (mHiViewPager != null && mScrollerDuration > 0) {
            mHiViewPager.setScrollerDuration(mScrollerDuration);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if (mLoop || mAutoPlay && mAdapter.getRealCount() != 0) {
            //无限轮播关键点：使第一张能反向滑动到最后一张，以达到无限滚动的效果
            int firstItem = mAdapter.getFirstItem();
            mHiViewPager.setCurrentItem(firstItem, false);
        }
        //清除缓存view
        mBanner.removeAllViews();
        mBanner.addView(mHiViewPager, layoutParams);
        mBanner.addView(mHiIndicator.get(), layoutParams);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mOnPageChangeListener && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mAdapter.getRealCount();
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mHiIndicator != null) {
            mHiIndicator.onPointChange(position, mAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}

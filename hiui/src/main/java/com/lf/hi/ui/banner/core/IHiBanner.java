package com.lf.hi.ui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.lf.hi.ui.banner.indicator.HiIndicator;

import java.util.List;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.core$
 * @ClassName: IHiBanner$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 11:50$
 * @Description:
 */
public interface IHiBanner {
    void setBannerData(@LayoutRes int layoutRes, @NonNull List<? extends HiBannerMo> models);

    void setBannerData(@NonNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator hiIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void setScrollerDuration(int duration);

    interface OnBannerClickListener{
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder,@NonNull HiBannerMo bannerMo,int position);
    }
}

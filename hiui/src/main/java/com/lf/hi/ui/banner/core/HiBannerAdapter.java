package com.lf.hi.ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.core$
 * @ClassName: HiBannerAdapter$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 11:57$
 * @Description:HiViewPager的适配器，为页面填充数据
 */
public class HiBannerAdapter extends PagerAdapter {
    private Context mContext;
    //
    private SparseArray<HiBannerViewHolder> mCachedViews = new SparseArray<>();
    private IHiBanner.OnBannerClickListener mBannerClickListener;
    private IBindAdapter mBindAdapter;
    private List<? extends HiBannerMo> models;
    //是否开启自动轮播
    private boolean mAutoPlay = true;
    //非自动轮播状态下是否可以循环切换
    private boolean mLoop = false;
    private int mLayoutResId = -1;

    public HiBannerAdapter(Context context) {
        this.mContext = context;
    }

    public void setBannerData(List<? extends HiBannerMo> models) {
        this.models = models;
        //初始化数据
        initCachedView();
        notifyDataSetChanged();
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.mBindAdapter = bindAdapter;
    }

    public void setBannerClickListener(IHiBanner.OnBannerClickListener bannerClickListener) {
        this.mBannerClickListener = bannerClickListener;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        //无效轮播的关键点
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    /**
     * 获取banner页面数量
     *
     * @return
     */
    public int getRealCount() {
        return models == null ? 0 : models.size();
    }

    /**
     * 获取初次展示的item位置
     *
     * @return
     */
    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        //之前如果添加过 就移除添加
        HiBannerViewHolder viewHolder = mCachedViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }
        //数据绑定
        onBind(viewHolder, models.get(realPosition), realPosition);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都会刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    protected void onBind(@NonNull final HiBannerViewHolder viewHolder, @NonNull final HiBannerMo bannerMo, final int position) {
        viewHolder.rootView.setOnClickListener(v -> {
            if (mBannerClickListener != null) {
                mBannerClickListener.onBannerClick(viewHolder, bannerMo, position);
            }
        });
        if (mBindAdapter != null) {
            mBindAdapter.onBind(viewHolder, bannerMo, position);
        }
    }

    private void initCachedView() {
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            HiBannerViewHolder viewHolder = new HiBannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCachedViews.put(i, viewHolder);
        }
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must be set setLayoutResId first");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }

    public static class HiBannerViewHolder {
        private SparseArray<View> viewSparseArray;
        View rootView;

        public HiBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (this.viewSparseArray == null) {
                this.viewSparseArray = new SparseArray<>(1);
            }
            V childView = (V) viewSparseArray.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                this.viewSparseArray.put(id, childView);
            }
            return childView;
        }
    }
}

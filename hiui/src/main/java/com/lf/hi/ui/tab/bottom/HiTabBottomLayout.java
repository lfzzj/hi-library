package com.lf.hi.ui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lf.hi.hilibrary.util.HiDisplayUtil;
import com.lf.hi.ui.R;
import com.lf.hi.ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: LF
 * @data on 2021/6/7 下午9:42
 * @desc TODO
 */
public class HiTabBottomLayout extends FrameLayout implements IHiTabLayout<HiTabBottom, HiTabBottomInfo<?>> {
    private List<OnTabSelectedListener<HiTabBottomInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabBottomInfo<?> selectedInfo;
    private float bottomAlpha;
    //TabBottom的高度
    private float tabBottomHight = 50;
    //TabBottom头部线条高度
    private float bottomLineHeight;
    //TabBottom头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<HiTabBottomInfo<?>> infoList;


    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    public HiTabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public HiTabBottom findTab(@NonNull HiTabBottomInfo<?> info) {
        ViewGroup vg = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            if (child instanceof HiTabBottom) {
                HiTabBottom tab = (HiTabBottom) child;
                if (tab.getHiTabInfo() == info) {
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabBottomInfo<?>> listener) {
        tabSelectedListeners.add(listener);

    }


    @Override
    public void defaultSelected(@NonNull HiTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabBottomInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        //移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i++) {
            removeViewAt(i);
        }
        selectedInfo = null;
        addBackground();
        //清除之前添加的HiTabBottom listener,  java foreach remove问题
        Iterator<OnTabSelectedListener<HiTabBottomInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabBottom) {
                iterator.remove();
            }
        }
        int height = HiDisplayUtil.dp2px(getContext(), tabBottomHight);
        FrameLayout fl = new FrameLayout(getContext());
        fl.setTag(TAG_TAB_BOTTOM);
        int width = HiDisplayUtil.getScreenWidthPx(getContext()) / infoList.size();
        for (int i = 0; i < infoList.size(); i++) {
            HiTabBottomInfo<?> info = infoList.get(i);
            //tips:为何不用LinearLayout，当动态改变child大小后Gravity.BOTTOM会失效
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;
            HiTabBottom tabBottom = new HiTabBottom(getContext());
            tabSelectedListeners.add(tabBottom);
            tabBottom.setHiTabInfo(info);
            fl.addView(tabBottom, params);
            tabBottom.setOnClickListener(v -> {
                onSelected(info);
            });
        }
        LayoutParams flParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        flParams.gravity = Gravity.BOTTOM;
        addBottomLine();
        addView(fl, flParams);
    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHight(float tabHight) {
        this.tabBottomHight = tabHight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    private void addBottomLine() {
        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));
        LayoutParams bottomLineParams = new LayoutParams(LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(getContext(), bottomLineHeight));
        bottomLineParams.gravity = Gravity.BOTTOM;
        bottomLineParams.bottomMargin = HiDisplayUtil.dp2px(getContext(), tabBottomHight - bottomLineHeight);
        addView(bottomLine, bottomLineParams);
        bottomLine.setAlpha(bottomAlpha);
    }

    private void onSelected(@NonNull HiTabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabBottomInfo<?>> listener : tabSelectedListeners) {
            listener.OnTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.hi_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(getContext(), tabBottomHight));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(bottomAlpha);
    }
}

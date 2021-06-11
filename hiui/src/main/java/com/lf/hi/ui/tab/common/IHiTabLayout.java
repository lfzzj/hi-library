package com.lf.hi.ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lf.hi.ui.tab.bottom.HiTabBottom;
import com.lf.hi.ui.tab.bottom.HiTabBottomInfo;

import java.util.List;

/**
 * @author: LF
 * @data on 2021/5/17 下午10:07
 * @desc TODO
 */
public interface IHiTabLayout<Tab extends ViewGroup,D> {
    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<HiTabBottomInfo<?>> listener);

    void defaultSelected(@NonNull D defaultInfo);

    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D>{
        void OnTabSelectedChange(int index, @Nullable D prevInfo,@Nullable D nextInfo);
    }
}

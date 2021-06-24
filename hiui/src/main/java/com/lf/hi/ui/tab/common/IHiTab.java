package com.lf.hi.ui.tab.common;

import androidx.annotation.Nullable;
import androidx.annotation.Px;

/**
 * @author: LF
 * @data on 2021/5/25 下午9:05
 * @desc HiTab对外接口
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@Nullable D data);

    /**
     * 动态的修改某个item的大小
     * @param height
     */
    void resetHeight(@Px int height);
}

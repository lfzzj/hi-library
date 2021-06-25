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
    /**
     * Tab -> 底部导航 / 顶部导航
     * D -> 数据
     */
    Tab findTab(@NonNull D data);

    //设置监听器
    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    //设置默认选中
    void defaultSelected(@NonNull D defaultInfo);

    //对数据进行相关的初始化
    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D>{
        /**
         *  被选中之后 会通知
         * @param index 选择的tab的位置索引
         * @param prevInfo 上一个选中tab的数据
         * @param nextInfo 下一个选中的数据
         */
        void OnTabSelectedChange(int index, @Nullable D prevInfo,@Nullable D nextInfo);
    }
}

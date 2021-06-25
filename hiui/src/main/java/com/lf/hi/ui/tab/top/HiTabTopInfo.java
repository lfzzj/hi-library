package com.lf.hi.ui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author: LF
 * @data on 2021/5/25 下午9:08
 * @desc HiTabTop的实体类
 */
public class HiTabTopInfo<Color> {
    public enum TabType {
        BITMAP, TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectBitmap;

    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    //bitmap
    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectBitmap = selectBitmap;
        this.tabType = TabType.BITMAP;
    }

    //text
    public HiTabTopInfo(String name,  Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.TEXT;
    }
}

package com.lf.hi.ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author: LF
 * @data on 2021/5/25 下午9:08
 * @desc HiTabBottom的实体类
 */
public class HiTabBottomInfo<Color> {
    public enum TabType {
        BITMAP, ICON
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectBitmap;
    public String iconFont;//字体

    public String defaultIconName;//默认IconName
    public String selectIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    //bitmap
    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectBitmap = selectBitmap;
        this.tabType = TabType.BITMAP;
    }

    //iconFont
    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectIconName = selectIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}

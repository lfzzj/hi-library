package com.lf.hi.ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author: LF
 * @data on 2021/5/25 下午9:08
 * @desc TODO
 */
public class HiTabBottomInfo<Color> {
    public enum TabType{
        BITMAP,ICON
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectBitmap;
    public String iconFont;

    public String defaultIconName;
    public String selectIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;


    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectBitmap = selectBitmap;
    }

    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectIconName = selectIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;

    }
}

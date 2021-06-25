package com.lf.hi.ui.tab.top;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.lf.hi.ui.R;
import com.lf.hi.ui.tab.common.IHiTab;

/**
 * @author: LF
 * @data on 2021/5/25 下午9:20
 * @desc 单个顶部导航栏
 */
public class HiTabTop extends RelativeLayout implements IHiTab<HiTabTopInfo<?>> {
    private HiTabTopInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabNameView;
    private View indicator;//指示器


    public HiTabTop(Context context) {
        this(context, null);
    }

    public HiTabTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tab_top, this);
        tabImageView = findViewById(R.id.iv_image);
        tabNameView = findViewById(R.id.tv_name);
        indicator = findViewById(R.id.tab_top_indicator);

    }

    public HiTabTopInfo<?> getHiTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }


    public TextView getTabNameView() {
        return tabNameView;
    }

    @Override
    public void setHiTabInfo(@Nullable HiTabTopInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    /**
     *
     * @param selected 是否被选中
     * @param init 是不是初始化
     */
    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == HiTabTopInfo.TabType.TEXT) {//iconFont
            if (init) {
                tabImageView.setVisibility(View.GONE);
                tabNameView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {//被选中时
                indicator.setVisibility(View.VISIBLE);
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            } else {//未选中时
                indicator.setVisibility(View.GONE);
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        } else if (tabInfo.tabType == HiTabTopInfo.TabType.BITMAP) {
            if (init) {
                tabImageView.setVisibility(View.VISIBLE);
                tabNameView.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected) {
                tabImageView.setImageBitmap(tabInfo.selectBitmap);
            } else {
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
            }
        }

    }

    /**
     * 改变某个tab的高度
     * @param height
     */
    @Override
    public void resetHeight(int height) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
        getTabNameView().setVisibility(View.GONE);
    }

    @Override
    public void OnTabSelectedChange(int index, @Nullable HiTabTopInfo<?> prevInfo, @Nullable HiTabTopInfo<?> nextInfo) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return;
        }
        //tabBottom被反选了
        if (prevInfo == tabInfo){
            inflateInfo(false,false);
        }else{
            inflateInfo(true,false);

        }
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        } else {
            return (int) color;
        }
    }
}

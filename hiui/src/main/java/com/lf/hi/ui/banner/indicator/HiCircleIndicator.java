package com.lf.hi.ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lf.hi.hilibrary.util.HiDisplayUtil;
import com.lf.hi.ui.R;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.indicator$
 * @ClassName: HiCircleIndicator$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 15:33$
 * @Description:圆形指示器
 */
public class HiCircleIndicator extends FrameLayout implements HiIndicator<FrameLayout> {
    //正常状态下的指示点
    private @DrawableRes
    int mPointNormal = R.drawable.shape_point_normal;
    //选中状态下的指示点
    private @DrawableRes
    int mPointSelect = R.drawable.shape_point_select;

    //指示点左右内间距
    private int mPointLeftRightPadding;
    //指示点上下内间距
    private int mPointTopBottomPadding;
    private int VWM = LinearLayout.LayoutParams.WRAP_CONTENT;

    public HiCircleIndicator(@NonNull Context context) {
        this(context, null);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(getContext(), 5);
        mPointTopBottomPadding = HiDisplayUtil.dp2px(getContext(), 15);

    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count <= 0) {
            return;
        }
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView;
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(VWM, VWM);
        imageViewParams.gravity = Gravity.CENTER_VERTICAL;
        imageViewParams.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding);
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(imageViewParams);
            if (i == 0) {
                imageView.setImageResource(mPointSelect);
            } else {
                imageView.setImageResource(mPointNormal);
            }
            groupView.addView(imageView);
        }
        LayoutParams groupViewParams = new LayoutParams(VWM, VWM);
        groupViewParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        addView(groupView,groupViewParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            if (i==current){
                imageView.setImageResource(mPointSelect);
            }else{
                imageView.setImageResource(mPointNormal);
            }
            imageView.requestLayout();
        }
    }
}

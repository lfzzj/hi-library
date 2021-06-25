package com.lf.hi.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lf.hi.ui.R;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.refresh$
 * @ClassName: HiTextOverView$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 10:18$
 * @Description:
 */
public class HiTextOverView extends HiOverView {
    private TextView mText;
    private View mRotateView;

    public HiTextOverView(@NonNull Context context) {
        super(context);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_refresh_overview, this, true);
        mText = findViewById(R.id.text);
        mRotateView = findViewById(R.id.iv_rotate);
    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {
        mText.setText("下拉刷新");
    }

    @Override
    public void onRefresh() {
        mText.setText("正在刷新");
        Animation oprareingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        oprareingAnim.setInterpolator(lin);
        mRotateView.startAnimation(oprareingAnim);
    }

    @Override
    public void onFinish() {
        mRotateView.clearAnimation();
    }

    @Override
    public void onOver() {
        mText.setText("松开刷新");
    }
}

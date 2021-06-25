package com.lf.hi.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.refresh$
 * @ClassName: HiRefreshLayout$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 17:59$
 * @Description:
 */
public class HiRefreshLayout extends FrameLayout implements HiRefresh {
    private HiOverView.HiRefreshState mState;
    private GestureDetector mGestureDetector;//手势监听器
    private HiRefreshListener mHiRefreshListener;
    protected HiOverView mHiOverView;
    private int mLastY;
    //刷新时是否禁止滚动
    private boolean disableRefreshScroll;
    private AutoScroller mScroller;

    public HiRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), hiGestureDetector);
        mScroller = new AutoScroller();
    }


    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinished() {
        View head = getChildAt(0);
        mHiOverView.onFinish();
        mHiOverView.setState(HiOverView.HiRefreshState.STATE_INIT);
        int bottom = head.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }
        mState = HiOverView.HiRefreshState.STATE_INIT;
    }

    @Override
    public void setHiRefreshListener(HiRefreshListener hiRefreshListener) {
        this.mHiRefreshListener = hiRefreshListener;
    }

    @Override
    public void setRefreshOverView(HiOverView hiOverView) {
        if (this.mHiOverView != null) {
            removeView(mHiOverView);
        }
        this.mHiOverView = hiOverView;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mHiOverView, 0, params);
    }

    HiGestureDetector hiGestureDetector = new HiGestureDetector() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceX) > Math.abs(distanceX) || mHiRefreshListener != null && !mHiRefreshListener.enableRefresh()) {
                //横向滑动，或刷新被禁止则不处理
                return false;
            }
            if (disableRefreshScroll && mState == HiOverView.HiRefreshState.STATE_REFRESH) {//刷新时是否禁止滚动
                return true;
            }
            View head = getChildAt(0);
            View child = HiScrollUtil.findScrollaleChild(HiRefreshLayout.this);
            if (HiScrollUtil.childScrolled(child)) {
                //如果列表发生了滚动则不处理
                return false;
            }
            //没有刷新或没有达到可以刷新的距离，且头部已经划出或下拉
            if (mState != HiOverView.HiRefreshState.STATE_REFRESH
                    || head.getBottom() <= mHiOverView.mPullRefreshHeight
                    && (head.getBottom() > 0 || distanceY <= 0.0f)) {
                //还在滑动中
                if (mState != HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    //速度计算
                    if (child.getTop() < mHiOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY / mHiOverView.minDamp);
                    } else {
                        seed = (int) (mLastY / mHiOverView.maxDamp);
                    }
                    //如果时正在刷新状态，则不允许在滑动的时候改变状态
                    boolean bool = moveDown(seed, true);
                    mLastY = (int) -distanceY;
                    return bool;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量
     * @param nonAuto 是否非自动滚动触发
     * @return
     */
    private boolean moveDown(int offsetY, boolean nonAuto) {
        View head = getChildAt(0);
        View child = getChildAt(1);
        int childTop = child.getTop() + offsetY;
        if (childTop <= 0) {//异常情况的补充
            offsetY = -child.getTop();
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {
                mState = HiOverView.HiRefreshState.STATE_INIT;
            }
        } else if (mState == HiOverView.HiRefreshState.STATE_REFRESH && childTop > mHiOverView.mPullRefreshHeight) {
            //如果正在下拉刷新中 禁止继续下拉
            return false;
        } else if (childTop <= mHiOverView.mPullRefreshHeight) {//还没超出设定的刷新距离
            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_VISIBLE && nonAuto) {//头部开始显示
                mHiOverView.onVisible();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_VISIBLE);
                mState = HiOverView.HiRefreshState.STATE_VISIBLE;
            }
            head.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);
            if (childTop == mHiOverView.mPullRefreshHeight && mState == HiOverView.HiRefreshState.STATE_OVER_RELEASE) {
                //下拉刷新完成
                refresh();
            }
        } else {
            if (mHiOverView.getState() != HiOverView.HiRefreshState.STATE_OVER && nonAuto) {
                //超出刷新位置
                mHiOverView.onOver();
                mHiOverView.setState(HiOverView.HiRefreshState.STATE_OVER);
                head.offsetTopAndBottom(offsetY);
                child.offsetTopAndBottom(offsetY);
            }
        }
        if (mHiOverView != null) {
            mHiOverView.onScroll(head.getBottom(), mHiOverView.mPullRefreshHeight);
        }
        return true;
    }

    /**
     * 刷新
     */
    private void refresh() {
        if (mHiRefreshListener != null) {
            mState = HiOverView.HiRefreshState.STATE_REFRESH;
            mHiOverView.onRefresh();
            mHiOverView.setState(HiOverView.HiRefreshState.STATE_REFRESH);
            mHiRefreshListener.onRefresh();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View head = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            //送开手
            if (head.getBottom() > 0) {
                if (mState != HiOverView.HiRefreshState.STATE_REFRESH) {  //非正在刷新
                    recover(head.getBottom());
                    return false;
                }
            }
            mLastY = 0;
        }
        boolean consumed = mGestureDetector.onTouchEvent(ev);
        if ((consumed || (mState != HiOverView.HiRefreshState.STATE_INIT && mState != HiOverView.HiRefreshState.STATE_REFRESH))
                && head.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);//让父类接收不到真实的事件
            return super.dispatchTouchEvent(ev);
        }
        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }

    }

    private void recover(int dis) {
        if (mHiRefreshListener != null && dis > mHiOverView.mPullRefreshHeight) {
            //滚动到指定位置dis-mHiOverView.mPullRefreshHeight
            mScroller.recover(dis - mHiOverView.mPullRefreshHeight);
            mState = HiOverView.HiRefreshState.STATE_OVER_RELEASE;
        } else {
            mScroller.recover(dis);

        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //定义head和child的排列位置
        View head = getChildAt(0);
        View child = getChildAt(1);
        if (head != null && child != null) {
            int childTop = child.getTop();
            if (mState == HiOverView.HiRefreshState.STATE_REFRESH) {
                head.layout(0, mHiOverView.mPullRefreshHeight - head.getMeasuredHeight(), right, mHiOverView.mPullRefreshHeight);
                child.layout(0, mHiOverView.mPullRefreshHeight, right, mHiOverView.mPullRefreshHeight + child.getMeasuredHeight());

            } else {
                head.layout(0, childTop - mHiOverView.mPullRefreshHeight, right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }
            View other;
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }
        }

    }

    public class AutoScroller implements Runnable {

        private Scroller mScroller;
        private int mLastY;
        private boolean mIsFinish;

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {//还未滚动完成
                moveDown(mLastY - mScroller.getCurrY(), false);
                mLastY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinish = true;
            }

        }

        void recover(int dis) {
            if (dis <= 0) {
                return;
            }
            removeCallbacks(this);
            mLastY = 0;
            mIsFinish = false;
            mScroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        boolean isIsFinished() {
            return mIsFinish;
        }
    }
}

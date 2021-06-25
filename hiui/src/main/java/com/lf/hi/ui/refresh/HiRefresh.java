package com.lf.hi.ui.refresh;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.refresh$
 * @ClassName: HiRefresh$
 * @Author: LF
 * @CreateDate: 2021/6/24$ 17:45$
 * @Description:
 */
public interface HiRefresh {
    /**
     * 刷新时是否禁止滑动
     *
     * @param disableRefreshScroll 是否禁止滑动
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新监听器
     * @param hiRefreshListener
     */
    void setHiRefreshListener(HiRefreshListener hiRefreshListener);

    void setRefreshOverView(HiOverView hiOverView);

    interface HiRefreshListener {
        void onRefresh();

        boolean enableRefresh();
    }

}

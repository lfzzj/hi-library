package com.lf.hi.ui.banner.indicator;

import android.view.View;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.core$
 * @ClassName: HiIndicator$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 11:51$
 * @Description:指示器
 */
public interface HiIndicator<T extends View>{
    T get();

    /**
     * 初始化Indicator
     * @param count 幻灯片的数量
     */
    void onInflate(int count);

    /**
     * 幻灯片切换回调
     * @param current 切换到幻灯片位置
     * @param count 幻灯片数量
     */
    void onPointChange(int current,int count);
}

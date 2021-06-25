package com.lf.hi.ui.banner.core;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.hi.ui.banner.core$
 * @ClassName: IBindAdapter$
 * @Author: LF
 * @CreateDate: 2021/6/25$ 11:55$
 * @Description:HiBanner的数据绑定接口，基于该接口可以实现数据的绑定和框架层解耦
 */
public interface IBindAdapter {
    void onBind(HiBannerAdapter.HiBannerViewHolder viewHolder, HiBannerMo mo, int position);
}

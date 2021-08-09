package com.lf.hi.hilibrary.restful

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiInterceptor$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 16:47$
 * @Description:
 */
interface HiInterceptor {
    fun intercept(chain: Chain): Boolean

    /**
     * Chain对象会在派发拦截器的时候创建
     */
    interface Chain {
        val isRequestPeriod: Boolean get() = false

        fun request(): HiRequest

        /**
         * 这个response对象 在网络发起之前是为空的
         */
        fun response(): HiResponse<*>?
    }
}
package com.lf.hi.hilibrary.restful

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiCallback$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 15:12$
 * @Description:callback回调
 */
interface HiCallback<T> {
    fun onSuccess(response:HiResponse<T>)
    fun onFailed(throwable: Throwable)
}
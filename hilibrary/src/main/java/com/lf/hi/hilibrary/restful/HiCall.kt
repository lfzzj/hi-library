package com.lf.hi.hilibrary.restful

import java.io.IOException

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiCall$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 15:17$
 * @Description:
 */
interface HiCall<T>{
    @Throws(IOException::class)
    fun execute():HiResponse<T>

    fun enqueue(callback: HiCallback<T>)

    interface Factory{
        fun newCall(request:HiRequest):HiCall<*>
    }
}
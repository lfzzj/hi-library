package com.lf.hi.library.http

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.lf.common.utils.SPUtil
import com.lf.hi.hilibrary.log.HiLog
import com.lf.hi.hilibrary.restful.HiInterceptor

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.http$
 * @ClassName:      BizInterceptor$
 * @Author:         LF
 * @CreateDate:     2021/7/29$ 16:11$
 * @Description:公共参数拦截器
 */
open class BizInterceptor : HiInterceptor {

    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        if (chain.isRequestPeriod) {
            val request = chain.request()
            val boardingPass = SPUtil.getString("boarding-pass") ?: ""
            request.addHeader("boarding-pass", boardingPass)
            request.addHeader("auth-token", "fd82dle882462e23b8e88aa82198f197")
        } else if (chain.response() != null) {
            HiLog.dt("BizInterceptor", chain.request().endPointUrl())
            HiLog.dt("BizInterceptor", chain.response()!!.rawData)
        }
        return false
    }



}
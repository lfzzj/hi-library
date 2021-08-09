package com.lf.hi.library.http.api

import com.alibaba.android.arouter.launcher.ARouter
import com.lf.hi.hilibrary.restful.HiInterceptor
import com.lf.hi.hilibrary.restful.HiResponse

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.http.api$
 * @ClassName:      HttpStatusInterceptor$
 * @Author:         LF
 * @CreateDate:     2021/8/3$ 16:06$
 * @Description:根据response的code 自动路由到相关页面
 */
class HttpStatusInterceptor : HiInterceptor {
    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        val response = chain.response()
        if (!chain.isRequestPeriod && response != null) {
            val code = response.code
            when (code) {
                HiResponse.RC_NEED_LOGIN -> {
                    ARouter.getInstance().build("/account/login").navigation()
                }
                HiResponse.RC_AUTH_TOKEN_EXPIRED or (HiResponse.RC_AUTH_TOKEN_INVALID) -> {
                    var helpUrl: String? = null
                    if (response.errorData != null) {
                        helpUrl = response.errorData!!.get("helpUrl")
                    }
                    ARouter.getInstance().build("/degrade/global/activity")
                        .withString("degrade_title", "非法访问")
                        .withString("degrade_desc", response.msg)
                        .withString("degrade_action", helpUrl)
                        .navigation()
                }
            }
        }
        return false
    }
}
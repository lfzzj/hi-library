package com.lf.hi.library.route

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import java.lang.RuntimeException

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.route$
 * @ClassName:      a$
 * @Author:         LF
 * @CreateDate:     2021/8/5$ 11:10$
 * @Description:
 */

@Interceptor(name = "biz_interceptor", priority = 9)
open class BizInterceptor : IInterceptor {

    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra
        if ((flag and (RouteFlag.FLAG_LOGIN) != 0)){
            callback!!.onInterrupt(RuntimeException("need login"))
            loginIntercept()
        }else{
            callback!!.onContinue(postcard)
        }
    }

    private fun loginIntercept() {
        Handler(Looper.getMainLooper()).post{
            Toast.makeText(context,"请先登录", Toast.LENGTH_SHORT).show()
            ARouter.getInstance().build("/account/login").navigation()
        }
    }


}

class RouteFlag {
    companion object{
        val FLAG_LOGIN = 1
    }
}
package com.lf.common.utils

import android.app.Application
import java.lang.Exception

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.utils$
 * @ClassName:      AppGlobals$
 * @Author:         LF
 * @CreateDate:     2021/7/30$ 16:35$
 * @Description:
 */
object AppGlobals {
    var application: Application? = null
    fun get(): Application? {
        try {
            if (application == null) {
                application =
                    Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                        .invoke(null) as Application
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return application
    }
}
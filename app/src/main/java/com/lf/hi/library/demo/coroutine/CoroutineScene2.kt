package com.lf.hi.library.demo.coroutine

import android.util.Log
import kotlinx.coroutines.delay

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.demo.coroutine$
 * @ClassName:      CoroutineScene2$
 * @Author:         LF
 * @CreateDate:     2021/7/20$ 14:18$
 * @Description:
 */
object CoroutineScene2 {
    private val TAG: String = "CoroutineScene2"

    suspend fun request2(): String {
        delay(2 * 1000)
        Log.e(TAG, "request2: completed" )
        return "result from request2"
    }
}
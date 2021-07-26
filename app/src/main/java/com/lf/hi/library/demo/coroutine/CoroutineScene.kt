package com.lf.hi.library.demo.coroutine

import android.util.Log
import kotlinx.coroutines.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.demo.coroutine$
 * @ClassName:      CoroutineScene$
 * @Author:         LF
 * @CreateDate:     2021/7/19$ 10:24$
 * @Description:
 */
object CoroutineScene {
    private val TAG: String = "CoroutineScene"

    /**
     * 依次启动三个子线程，并且同步的方式拿到他们的返回值，进而更新UI
     */
    fun startScene1() {
        //Dispatchers.Main .IO .Default(默认) .Unconfined
        GlobalScope.launch(Dispatchers.Main) {
            Log.e(TAG, "Coroutine is running")
            val result1 = request1()
            val result2 = request2(result1)
            val result3 = request3(result2)
            updataUI(result3)
        }
        Log.e(TAG, "Coroutine is launched")
    }

    /**
     * 启动一个线程，先执行request1完成之后同时运行request2和request3，并发结束之后才更新UI
     */
    fun startScene2() {
        GlobalScope.launch(Dispatchers.Main) {
            val result1 = request1()
            val deferred2 = GlobalScope.async { request2(result1) }
            val deferred3 = GlobalScope.async { request3(result1) }
            updataUI(deferred2.await(),deferred3.await())

        }
    }
    private fun updataUI(result2: String,result3: String){
        Log.e(TAG, "updataUI work on ${Thread.currentThread().name}")
        Log.e(TAG, "paramter:${result2} -- ${result3}")
    }
    private fun updataUI(result: String) {
        Log.e(TAG, "updataUI work on ${Thread.currentThread().name}")
        Log.e(TAG, "paramter:${result}")
    }

    /**
     * suspend关键字的作用
     * delay既然是io异步任务，是如何做到延迟协程中的代码向下执行的
     */
    suspend fun request1(): String {
        delay(2 * 1000)//不会暂停线程，但会暂停当前所在的协程
        Log.e(TAG, "request1 work on ${Thread.currentThread().name}")
        return "request from request1"
    }

    suspend fun request2(request1: String): String {
        delay(2 * 1000)
        Log.e(TAG, "request2 work on ${Thread.currentThread().name}")
        return "request from request2"
    }

    suspend fun request3(request2: String): String {
        delay(2 * 1000)
        Log.e(TAG, "request3 work on ${Thread.currentThread().name}")
        return "request from request3"
    }
}
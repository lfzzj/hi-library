package com.lf.hi.library.demo.coroutine

import android.content.res.AssetManager
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import kotlin.concurrent.thread

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.demo.coroutine$
 * @ClassName:      CoroutineScene3$
 * @Author:         LF
 * @CreateDate:     2021/7/20$ 15:02$
 * @Description:演示以异步的方式读取asstes目录下的文件，并且适配协程的写法，让他真正的挂起函数
 */
object CoroutineScene3 {
    suspend fun parseAssetsFile(assetManager: AssetManager, fileName: String): String {
        return suspendCancellableCoroutine { continuation ->
            thread {
                Runnable {
                    val inputStream = assetManager.open(fileName)
                    var br = BufferedReader(InputStreamReader(inputStream))
                    var line: String?
                    var stringBuilder = StringBuilder()

                    do {
                        line = br.readLine()
                        if (line != null) stringBuilder.append(line) else break
                    } while (true)
                    inputStream.close()
                    br.close()
                    Thread.sleep(2000)
                    continuation.resumeWith(Result.success(stringBuilder.toString()))
                }
            }.start()
        }
    }
}
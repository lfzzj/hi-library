package com.lf.hi.library

import com.lf.hi.library.AtomicDemo.AtomicTask
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val tast = AtomicTask()
        val runnable = Runnable {
            for (i in 0..9999) {
                tast.incrementAtomic()
                tast.incrementVolatile()
            }
        }
        val t1 = Thread(runnable)
        val t2 = Thread(runnable)
        t1.start()
        t2.start()

        t1.join()
        t2.join()
        println("原子类结果：" + tast.atomicInteger.get())
        println("volatile结果：" + tast.volatileCount)
    }
}
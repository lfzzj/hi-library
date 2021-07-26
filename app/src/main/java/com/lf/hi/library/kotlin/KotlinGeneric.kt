package com.lf.hi.library.kotlin

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.kotlin$
 * @ClassName:      KotlinGeneric$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 14:56$
 * @Description:泛型
 */

fun main() {

}

interface Drinks<T> {
    fun taste(): T
    fun price(t: T)
}

class Sweet {
    val price = 5
}

class Coke : Drinks<Sweet> {
    override fun taste(): Sweet {
        return Sweet()
    }

    override fun price(t: Sweet) {
        println("price ${t.price}")
    }
}

/***
 * 泛型类
 */
abstract class Color<T>(var t: T) {
    abstract fun printerColor()
}

class Blue {
    val color = "Blue"
}

class BlueColor(t: Blue) : Color<Blue>(t) {
    override fun printerColor() {
        println("color ${t.color}")
    }
}

/**
 * 泛型方法
 */
fun <T> fromJson(json: String, tClass: Class<T>): T? {
    val t: T = tClass.newInstance()
    return t
}

/**
 * 泛型约束
 */
fun <T : Comparable<T>?> sort(list: List<T>?): Unit {

}

fun test2() {
    sort(listOf(1, 2, 3))
//    sort(listOf(Blue())) // 报错
}

//多个上界
fun <T> test(list: List<T>, threshold: T): List<T>
        where T : CharSequence,
              T : Comparable<T> {
    return list.filter { it > threshold }.map { it }
}

/**
 * java 与 kotlin 泛型的异同
 *              java        kotlin
 * 有界类型参数  extends      :
 * 上界通配符    ? extends   out(协变)
 * 下界通配符    ? super     in(逆变)
 */
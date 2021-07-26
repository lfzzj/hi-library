package com.lf.hi.library.kotlin

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.kotlin$
 * @ClassName:      LambleTesc$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 10:58$
 * @Description:lamble
 */

fun test1() {
    println("无参数")
}

val test1L = { println("无参数") }

fun test2(a: Int, b: Int): Int {
    return a + b
}

fun test2L(a: Int, b: Int) = a + b
val text2L1 = { a: Int, b: Int -> a + b }

fun test3() {
    val arr = arrayOf(1, 2, 3, 4, 5)

    val map = mapOf("key1" to "value1", "key2" to "value2", "key3" to "value3")
    map.forEach { (key, value) ->
        println(value)
    }

    //_表示不用的参数
    map.forEach { (_, value) ->
        println(value)
    }

    map.apply { }
}

fun main() {
    val list = listOf(1, 2, 3)
    val sum = list.sum { println("it:${it}") }
    println("sum = ${sum}")

    val listStr = listOf("1", "2", "3")
    val result = listStr.toIntSum()(2)
    println("result = ${result}")

    testClosure(1)(2) {
        println(it)
    }

}

/**
 * 高阶函数-函数作为参数
 */
fun List<Int>.sum(callback: (Int) -> Unit): Int {
    var result = 0
    for (v in this) {
        result += v
        callback(v)
    }
    return result
}

/**
 * 高阶函数-函数作为返回值
 */
fun List<String>.toIntSum(): (scale: Int) -> Float {
    return fun(scale): Float {
        var result = 0f
        for (v in this) {
            result += v.toInt() * scale
        }
        return result
    }
}

/**
 * 闭包
 */
fun testClosure(v1: Int): (v2: Int, (Int) -> Unit) -> Unit {
    return fun(v2: Int, printer: (Int) -> Unit) {
        printer(v1 + v2)
    }
}

data class Result(var message: String, val cade: Int)

fun test() {
    var result = Result("success", 0)
    var (message, code) = result
    println("message = ${message} code = ${code}")
}

val fun1 = fun(x: Int, y: Int): Int {
    return x + y
}

//方法字面值 -> num -> (num > 10)
fun literal() {
    var temp: ((Int) -> Boolean?)? = null
    temp = { num -> (num > 10) }
    temp(11)

}
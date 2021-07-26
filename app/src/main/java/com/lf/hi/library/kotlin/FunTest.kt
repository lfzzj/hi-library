package com.lf.hi.library.kotlin

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library$
 * @ClassName:      FunTest$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 9:34$
 * @Description:方法
 */

fun main() {

}

class Person {
    //成员方法
    fun test1() {}

    companion object {
        //静态方法
        fun test2() {}
    }
}

//静态类
object NumUtil {
    fun double(num: Int): Int {
        return num * 2
    }
}

//单表达式方法，当方法返回单个表达式时，可以省略花括号并且在 = 号之后指定代码体即可
fun double(x: Int): Int = x * 2

//默认值，方法参数可以有默认值，当省略相应的参数时使用默认值，与java相比，可以减少重载数量
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {}

//可变参数
fun append(vararg str: Char): String {
    val result = StringBuffer()
    for (char in str) {
        result.append(char)
    }
    return result.toString()
}

//局部方法
fun magic(): Int {
    fun foo(v: Int): Int {
        return v * v
    }

    val v1 = (0..0).random() //0到100的随机数
    return foo(v1)
}


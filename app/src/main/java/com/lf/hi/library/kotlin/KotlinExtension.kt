package com.lf.hi.library.kotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import java.util.ArrayList

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.kotlin$
 * @ClassName:      KotlinExtension$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 16:09$
 * @Description:扩展
 */

fun main() {
    val list = mutableListOf(1, 2, 3)
    list.swap(0, 2)
    println("$list")
}

//扩展元素互换
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

//泛型扩展
fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

val String.lastChar: Char
    get() = this.get(this.length - 1)

class Jump{
    companion object{}
}

//为伴生对象添加扩展
fun Jump.Companion.print(str:String){
    println(str)
}


/**
 * let
 */
fun testLet(str: String?){
    //避免为null的操作
    str?.let {
        it.length
    }
    //限制作用域
    str.let {
        val str2 = "let"
        println(it + str)
    }
}

data class Room(val address:String,val price:Float,val size :Float)

/**
 * run
 */
fun testRun(room: Room){
    room.run {
        println("$address  $price  $size")
    }
}
/**
 * apply
 */
fun testApply(){
    ArrayList<String>().apply {
        add("1")
        add("1")
    }.let {
        println(it)
    }
}

fun <T : View> Activity.find(@IdRes id: Int):T{
    return findViewById(id)
}

fun Int.onClick(activity: Activity,click:() ->Unit){
    activity.find<View>(this).apply {
        setOnClickListener {
            click()
        }
    }
}

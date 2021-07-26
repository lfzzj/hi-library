package com.lf.hi.library.kotlin

import android.view.View
import java.util.*

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.kotlin$
 * @ClassName:      KotlinClass$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 11:38$
 * @Description:类
 */
fun main() {

}

/**
 * 主构造方法 constructor 可以省略
 */
class KotlinClass constructor(name: String) {
    //次构造方法可必须要调用主构造方法  次构造方法可以有多个
    constructor(view: View, name: String) : this(name)
}

//能被继承的话必须要open修饰
open class Animal(age: Int) {
    init {
        println("age=${age}")
    }

    open val foot: Int = 0

    //方法也需要open 才能被子类覆盖
    open fun eat() {

    }
}

class Dog : Animal {
    constructor(age: Int) : super(age)

//    val simple :Int  //如果在定义的时候没有初始化，必须要在构造方法中初始化 或者设置get方法

    override val foot = 4
    override fun eat() {

    }
}

class Shop {
    val name: String = "Android"
    val address: String? = null
    val isClose: Boolean
        get() = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 11
    var score: Float = 0.0f
        get() = if (field < 0.2f) 0.2f else field * 1.5f
        set(value) {
            println(value)
        }

}

class Test{
    lateinit var shop:Shop //延迟初始化
    fun setup(){
        shop = Shop()
    }
    fun test(){
        //::表示创建成员引用或类引用
        if (::shop.isInitialized) println(shop.address)
    }
}

//抽象类
abstract class Printer{
    abstract fun print()
}

class FilePrinter:Printer(){
    override fun print() {

    }
}

interface Stydy{
    val time:Int //抽象的
    fun discuss()

    fun learnCourses(){
        println("")
    }
}

class StudyAs(override val time: Int) :Stydy{
    override fun discuss() {

    }

}

interface A{
    fun foo(){
        println("A")
    }
}

interface B{
    fun foo(){
        println("B")
    }
}

class D:A,B{
    override fun foo() {
        super<A>.foo()
    }

}

//数据类型 不能定义成open或者抽象
data class Address(val name:String,val number:Int){
    val city:String =""
    fun print(){
        println(city)
    }
}

open class Address2(name:String){
    open fun print(){

    }
}

class Shop2{
    var address: Address2? = null
    fun addAddress(address2: Address2){
        this.address = address2
    }
}

class Student(val name:String){
    //伴生对象
    companion object{
        val student = Student("android")
        fun study(){
            println("android 架构师")
        }
    }
}
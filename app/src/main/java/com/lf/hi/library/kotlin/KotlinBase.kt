package com.lf.hi.library.kotlin


/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.kotlin$
 * @ClassName:      KotlinBase$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 9:49$
 * @Description:基础
 */
fun main() {

    type()
    arrayType()
}

//数据类型
fun type() {
    val num1 = 1.68 //Double
    val num2 = 2 //Int
    val num3 = 1f // Float

    println("num1:$num1 num2:$num2 num3:$num3")
    printType(num1)
}

fun printType(param: Any) {
    println("$param is ${param::class.simpleName} type")
}

//数组
fun arrayType() {
    val array = arrayOf(1, 2, 3)

    val array1 = arrayOfNulls<Int>(3)
    array[0] = 4
    array[0] = 4
    array[0] = 4

    //构造函数
    val array2 = Array(5) { i -> (i * i).toString() }

    val x: IntArray = intArrayOf(1, 2, 3)

    //大小为5 值为[0,0,0,0,0]的整形数组
    val array3 = IntArray(5)

    //大小为5 值为[2,2,2,2,2]的整形数组
    val array4 = IntArray(5) { 2 }

    //大小为5 值为[0,1,2,3,4]的整形数组 (值初始化为其索引值)
    val array5 = IntArray(5) { it * 1 }

    /**
     * 数组遍历
     */
    //1.
    for (item in array) {

    }

    //2.带索引遍历数组
    for (i in array.indices) {
        println("$i -> ${array[i]}")
    }

    //3.遍历元素（带索引）
    for ((index, item) in array.withIndex()) {
        println("$index -> $item ")
    }

    //4.
    array.forEach { index ->
        println("$index -> ${array[index]}")
    }

    //5.
    array.forEachIndexed { index, item -> println("$index -> $item ") }
}

fun collectionType() {

    val stringList = listOf("a", "b", "v")

    //元素唯一
    val stringSet = setOf("a", "b", "a")

    //可变集合
    val numbers = mutableListOf(1, 2, 3, 4)
    numbers.add(5)
    numbers.removeAt(1)
    numbers[0] = 0
}

/**
 * 集合排序
 */
fun collectionSort() {
    val numbers = mutableListOf(1, 2, 3, 4)
    //随机排序
    numbers.shuffle()

    numbers.sort() //从小到大
    numbers.sortDescending()//从大到小

    //条件排序
    data class Language(var name: String, val score: Int)

    val languageList: MutableList<Language> = mutableListOf()
    //……添加数据 根据分数进行单条件排序
    languageList.sortBy { it.score }

    //多条件进行排序，第一条件score，第二条件name
    languageList.sortWith(compareBy({ it.score }, { it.name }))
}

//Set集合
fun set(){
    //不重复
}

//map集合
fun map(){
    //键值对
    mapOf("a" to 1,"b" to 2)
}


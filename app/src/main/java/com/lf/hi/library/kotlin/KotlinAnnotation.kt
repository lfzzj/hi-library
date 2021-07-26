package com.lf.hi.library.kotlin

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.kotlin$
 * @ClassName:      KotlinAnnotation$
 * @Author:         LF
 * @CreateDate:     2021/6/28$ 15:19$
 * @Description:注解
 */
//和一般的声明很类似 只是在class前面加上了annotation修饰符
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class ApiDoc(val value: String)

class Box {

}

public enum class Method {
    GET, POST
}

@Target( AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpMethod(val method: Method)

interface Api {
    val name: String
    val version: String
        get() = "1.0"
}

@HttpMethod(Method.GET)
class ApiGetArticles : Api {
    override val name: String
        get() = "/api.articles"
}

fun fire(api: Api) {
    val annotations = api.javaClass.annotations //获取所有的注解
    val method = annotations.find { it is HttpMethod } as? HttpMethod
    println("${method?.method} 方式请求")
}

fun main() {
    fire(ApiGetArticles())
}
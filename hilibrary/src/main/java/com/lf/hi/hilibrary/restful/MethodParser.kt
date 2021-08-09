package com.lf.hi.hilibrary.restful

import com.lf.hi.hilibrary.restful.annotation.*
import java.lang.IllegalStateException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.NoSuchElementException

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      MethodParser$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 17:03$
 * @Description:
 */
class MethodParser(val baseUrl: String, method: Method, args: Array<Any>?) {
    private var domainUrl: String? = null
    private var formPost: Boolean = true
    private var httpMethod: Int = 0
    private lateinit var relativeUrl: String
    private var returnType: Type? = null
    private var headers: MutableMap<String, String> = mutableMapOf()
    private var parameters: MutableMap<String, String> = mutableMapOf()

    init {
        //解析 注解
        parseMethodAnnotations(method)

        //解析 入参
        if(args!=null){
            parseMethodParameters(method, args)
        }

        //解析 返回值
        parseMethodReturnType(method)
    }


    private fun parseMethodAnnotations(method: Method) {
        val annotations = method.annotations
        for (annotation in annotations) {
            if (annotation is GET) {
                relativeUrl = annotation.value
                httpMethod = HiRequest.MEHTOD.GET
            } else if (annotation is POST) {
                relativeUrl = annotation.value
                httpMethod = HiRequest.MEHTOD.POST
                formPost = annotation.formPost
            } else if (annotation is Headers) {
                val headersArray = annotation.value
                for (header in headersArray) {
                    val colon = header.indexOf(":")
                    check(!(colon == 0 || colon == -1)) {
                        String.format(
                            "@headers value must be in the form [name:value],but fount [%s]",
                            header
                        )
                    }
                    val name = header.substring(0, colon)
                    val value = header.substring(colon + 1).trim()
                    headers[name] = value
                }
            } else if (annotation is BaseUrl) {
                domainUrl = annotation.value
            } else {
                throw IllegalStateException("connot handle annotation:" + annotation.javaClass.toString())
            }
        }
        require((httpMethod == HiRequest.MEHTOD.GET) || (httpMethod == HiRequest.MEHTOD.POST)) {
            String.format("method %s must has one of GET,POST", method.name)
        }
        if (domainUrl == null) {
            domainUrl = baseUrl
        }
    }

    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        val parameterAnnotations = method.parameterAnnotations
        val equals = parameterAnnotations.size == args.size
        require(equals) {
            String.format(
                "arguments annotations count %s dont match expect count %s",
                parameterAnnotations.size,
                args.size
            )
        }
        for (index in args.indices) {
            val annotations = parameterAnnotations[index]
            require(annotations.size <= 1) { "filed can only has one annotion :index=$index" }
            val value = args[index]
            require(isPrimitive(value)) { "8 basic types are supported for now,index=$index" }
            val annotation = annotations[0]
            if (annotation is Filed) {
                val key = annotation.value
                val value = args[index]
                parameters[key] = value.toString()
            } else if (annotation is Path) {
                val replaceName = annotation.value
                val replacement = value.toString()
                if (replaceName != null && replacement != null) {
                    val newRelativeUrl = relativeUrl.replace("${replaceName}", replacement)
                    relativeUrl = newRelativeUrl
                }
            } else {
                throw IllegalStateException("cannot handle parameter annotion:" + annotation.javaClass.toString())
            }
        }
    }

    private fun isPrimitive(value: Any): Boolean {
        if (value.javaClass == String::class.java) {
            return true
        }
        try {
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
        }
        return false
    }


    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != HiCall::class.java) {
            throw IllegalStateException(
                String.format(
                    "method %s must be type of HiCall.class",
                    method.name
                )
            )
        }
        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) {
                "method %s can only has one generic return type"
            }
            returnType = actualTypeArguments[0]
        } else {
            throw IllegalStateException(
                String.format(
                    "method %s must has one generic return type",
                    method.name
                )
            )
        }
    }

    fun newRequest(): HiRequest {
        val request = HiRequest()
        request.domainUrl = domainUrl
        request.returnType = returnType
        request.relativeUrl = relativeUrl
        request.parameters = parameters
        request.headers = headers
        request.httpMethod = httpMethod
        request.formPost = formPost
        return request
    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>?): MethodParser {
            return MethodParser(baseUrl, method, args)
        }

    }
}
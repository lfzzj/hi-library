package com.lf.hi.hilibrary.restful

import androidx.annotation.IntDef
import java.lang.IllegalStateException
import java.lang.reflect.Type

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiRequest$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 15:20$
 * @Description:
 */
open class HiRequest {
    @MEHTOD
    var httpMethod: Int = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, String>? = null
    var domainUrl: String? = null
    var relativeUrl: String? = null
    var returnType: Type? = null
    var formPost: Boolean = true

    @IntDef(value = [MEHTOD.GET, MEHTOD.POST])
    annotation class MEHTOD {
        companion object {
            const val GET = 0
            const val POST = 1
        }
    }

    //返回的是请求的完整的url
    /**
     * https://api.demo.com/v1/   ---relativeUrl:user/login  --->  https://api.demo.com/v1/user/login
     * 有可能存在别的域名场景
     * https://api.demo.com/v2/
     *
     * https://api.demo.com/v1/   ---relativeUrl:/v2/user/login  --->https://api.demo.com/v2/user/login
     */
    fun endPointUrl(): String {
        if (relativeUrl == null) {
            throw IllegalStateException("relative url must not be null")
        }
        if (!relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }
        val indexOf = domainUrl!!.indexOf("/")
        return domainUrl!!.substring(0, indexOf) + relativeUrl
    }

    fun addHeader(name: String, value: String) {
        if (headers == null) {
            headers = mutableMapOf()
        }
        headers!![name] = value
    }
}
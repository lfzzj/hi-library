package com.lf.hi.library.http

import com.lf.hi.hilibrary.restful.HiRestful
import com.lf.hi.library.http.api.HttpStatusInterceptor

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.http$
 * @ClassName:      ApiFactory$
 * @Author:         LF
 * @CreateDate:     2021/7/29$ 16:08$
 * @Description:
 */
object ApiFactory {
        private val baseUrl = "https://api.devio.org/as/"
//    private val baseUrl = "https://gank.io/api/v2/"
//    private val baseUrl = "http://localhost/"

    private val hiRestful: HiRestful = HiRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        hiRestful.addInterceptor(BizInterceptor())
        hiRestful.addInterceptor(HttpStatusInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }

}
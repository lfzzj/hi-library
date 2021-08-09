package com.lf.hi.hilibrary.restful

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiRestful$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 16:58$
 * @Description:
 */
open class HiRestful constructor(var baseUrl: String, val callFactory: HiCall.Factory) {
    private var interceptors: MutableList<HiInterceptor> = mutableListOf()
    private val methodService: ConcurrentHashMap<Method, MethodParser> = ConcurrentHashMap()
    private var scheduler: Scheduler
    fun addInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }

    init {
        scheduler = Scheduler(callFactory, interceptors)
    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            var methodParser = methodService.get(method)
            if (methodParser == null) {
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService.put(method, methodParser)
            }
            val request = methodParser.newRequest()
//            callFactory.newCall(request)
            scheduler.newCall(request)
        } as T
    }
}
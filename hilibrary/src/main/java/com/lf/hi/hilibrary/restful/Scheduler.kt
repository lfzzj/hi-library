package com.lf.hi.hilibrary.restful

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      Scheduler$
 * @Author:         LF
 * @CreateDate:     2021/7/27$ 14:38$
 * @Description:代理CallFactory创建出来的call对象，从而实现拦截器的派发动作
 */
class Scheduler(
    val callFactory: HiCall.Factory,
    val interceptors: MutableList<HiInterceptor>
) {
    fun newCall(request: HiRequest): HiCall<*> {
        val newCall: HiCall<*> = callFactory.newCall(request)
        return ProxyCall(newCall, request)
    }

    internal inner class ProxyCall<T>(val delegate: HiCall<T>, val request: HiRequest) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            dispatchinterceptor(request, null)
            val reponse = delegate.execute()
            dispatchinterceptor(request, reponse)
            return reponse
        }

        override fun enqueue(callback: HiCallback<T>) {
            dispatchinterceptor(request, null)
            delegate.enqueue(object : HiCallback<T> {
                override fun onSuccess(response: HiResponse<T>) {
                    dispatchinterceptor(request, response)
                    if (callback != null) callback.onSuccess(response)
                }

                override fun onFailed(throwable: Throwable) {
                    if (callback != null) callback.onFailed(throwable)
                }
            })
        }

        private fun dispatchinterceptor(request: HiRequest, response: HiResponse<T>?) {
            InterceptorChain(request, response).dispatch()
        }

        internal inner class InterceptorChain(
            val request: HiRequest,
            val response: HiResponse<T>?
        ) : HiInterceptor.Chain {
            //分发的是第几个拦截器
            var callIndex: Int = 0

            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): HiRequest {
                return request
            }

            override fun response(): HiResponse<*>? {
                return response
            }

            fun dispatch() {
                val interceptor: HiInterceptor = interceptors[callIndex]
                val intercept = interceptor.intercept(this)
                callIndex++
                if (!intercept && callIndex < interceptors.size) {
                    dispatch()
                }
            }

        }
    }
}
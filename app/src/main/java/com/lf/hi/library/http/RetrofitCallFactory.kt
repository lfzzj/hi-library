package com.lf.hi.library.http

import com.lf.hi.hilibrary.restful.*
import com.lf.hi.library.kotlin.Method
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import retrofit2.http.Headers
import java.lang.IllegalStateException
import javax.xml.transform.OutputKeys.METHOD


/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.http$
 * @ClassName:      RetrofitCallFactory$
 * @Author:         LF
 * @CreateDate:     2021/7/29$ 9:58$
 * @Description:
 */
class RetrofitCallFactory(val baseUrl: String) : HiCall.Factory {
    private var hiConvert: HiConvert
    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).build()
        apiService = retrofit.create(ApiService::class.java)
        hiConvert = GsonConvert()
    }

    override fun newCall(request: HiRequest): HiCall<Any> {
        return RetrofitCall(request)
    }

    internal inner class RetrofitCall<T>(val request: HiRequest) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            val realCall = createRealCall(request)
            val response = realCall.execute()
            return parseResponse(response)
        }

        override fun enqueue(callback: HiCallback<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable) {
                    callback.onFailed(throwable = t)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val response = parseResponse(response)
                    callback.onSuccess(response)
                }
            })
        }

        private fun parseResponse(response: Response<ResponseBody>): HiResponse<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = response.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return hiConvert.convert(rawData!!, request.returnType!!)
        }


        private fun createRealCall(request: HiRequest): Call<ResponseBody> {
            if (request.httpMethod == HiRequest.MEHTOD.GET) {
                return apiService.get(request.headers, request.endPointUrl(), request.parameters)
            } else if (request.httpMethod == HiRequest.MEHTOD.POST) {
                val parameters = request.parameters
                var builder = FormBody.Builder()
                var requestBody: RequestBody? = null
                var jsonObject = JSONObject()
                for ((key, value) in parameters!!) {
                    if (request.formPost) {
                        builder.add(key, value)
                    } else {
                        jsonObject.put(key, value)
                    }
                }
                if (request.formPost) {
                    requestBody = builder.build()
                } else {
                    requestBody = RequestBody.create(
                        MediaType.parse("application/json;utf-8"),
                        jsonObject.toString()
                    )
                }
                return apiService.post(request.headers, request.endPointUrl(), requestBody)
            } else {
                throw IllegalStateException("hirestful only support GET POST for now ,url = ${request.endPointUrl()}")
            }
        }
    }

    interface ApiService {
        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>
    }
}
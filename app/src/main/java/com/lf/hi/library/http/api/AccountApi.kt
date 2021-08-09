package com.lf.hi.library.http.api

import com.lf.hi.hilibrary.restful.HiCall
import com.lf.hi.hilibrary.restful.annotation.Filed
import com.lf.hi.hilibrary.restful.annotation.GET
import com.lf.hi.hilibrary.restful.annotation.POST
import com.lf.hi.library.model.CourseNotice
import com.lf.hi.library.model.UserProfile
import org.json.JSONObject

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.http$
 * @ClassName:      TextApi$
 * @Author:         LF
 * @CreateDate:     2021/7/29$ 16:26$
 * @Description:
 */
interface AccountApi {

    @GET("cities")
    fun listCities(@Filed("name") name: String): HiCall<JSONObject>

    @POST("user/login")
    fun login(
        @Filed("username") username: String,
        @Filed("password") password: String
    ): HiCall<String>

    @POST("user/registeration")
    fun register(
        @Filed("username") username: String,
        @Filed("password") password: String
    ): HiCall<String>

    @GET("user/profile")
    fun profile(): HiCall<UserProfile>

    @GET("notice")
    fun notice(): HiCall<CourseNotice>
}
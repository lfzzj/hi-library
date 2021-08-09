package com.lf.hi.library.http.api

import com.lf.hi.hilibrary.restful.HiCall
import com.lf.hi.hilibrary.restful.annotation.GET
import com.lf.hi.library.model.CourseNotice
import com.lf.hi.library.model.TabCategory

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.http.api$
 * @ClassName:      HomeApi$
 * @Author:         LF
 * @CreateDate:     2021/8/9$ 14:27$
 * @Description:
 */
interface HomeApi {
    @GET("category/categories")
    fun queryTabList(): HiCall<List<TabCategory>>


}
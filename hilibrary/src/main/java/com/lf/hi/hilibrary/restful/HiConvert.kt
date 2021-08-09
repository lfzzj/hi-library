package com.lf.hi.hilibrary.restful

import java.lang.reflect.Type

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiConvert$
 * @Author:         LF
 * @CreateDate:     2021/7/29$ 15:42$
 * @Description:
 */
interface HiConvert {
    fun <T> convert(rawData:String,dataType: Type):HiResponse<T>
}
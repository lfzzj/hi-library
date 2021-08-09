package com.lf.hi.hilibrary.restful

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      HiResponse$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 15:13$
 * @Description:响应报文
 */
open class HiResponse<T> {
    companion object {
        val SUCCESS: Int = 0
        val RC_HAS_ERROR = 5000  //有错误
        val RC_ACCOUNT_INVALID = 5001 //账号不存在
        val RC_PWD_INVALID = 5002  //密码错误
        val RC_NEED_LOGIN = 5003  //请先登录
        val RC_AUTH_TOKEN_EXPIRED = 4030  //访问token过期，请重新设置
        val RC_AUTH_TOKEN_INVALID = 4031  //访问Token不正确，请重新设置
    }

    var rawData: String? = null//原始数据
    var code = 0//业务状态码
    var data: T? = null //业务数据
    var errorData: Map<String, String>? = null//错误状态下的数据

    var msg: String? = null//错误信息

    fun successful(): Boolean {
        return code == SUCCESS
    }
}
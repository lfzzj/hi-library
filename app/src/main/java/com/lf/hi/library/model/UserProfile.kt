package com.lf.hi.library.model

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.model$
 * @ClassName:      UserProfile$
 * @Author:         LF
 * @CreateDate:     2021/8/5$ 16:14$
 * @Description:
 */
data class UserProfile(
    val isLogin: Boolean,
    val facoriteCount: Int,
    val browseCount: Int,
    val learnMinutes: Int,
    val userName: String,
    val avatar: String,
    val bannerNoticeList:List<Notice>
)
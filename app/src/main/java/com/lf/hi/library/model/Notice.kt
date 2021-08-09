package com.lf.hi.library.model

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.model$
 * @ClassName:      Notice$
 * @Author:         LF
 * @CreateDate:     2021/8/5$ 16:18$
 * @Description:
 */
data class Notice(
    val id: String,
    val sticky: Int,
    val valtype: String,
    val title: String,
    val subtitle: String,
    val url: String,
    val cover: String,
    val createTime: String
)
package com.lf.hi.library.model
import androidx.annotation.Keep


/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.library.model$
 * @ClassName:      HomeModel$
 * @Author:         LF
 * @CreateDate:     2021/8/9$ 14:28$
 * @Description:
 */
/**
{
"categoryId":"1",
"categoryName":"热门",
"goodsCount":"1"
}
 */
data class TabCategory(val categoryId: String, val categoryName: String, val goodsCount: String)

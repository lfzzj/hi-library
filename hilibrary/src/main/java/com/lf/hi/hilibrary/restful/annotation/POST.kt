package com.lf.hi.hilibrary.restful.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 *
 * @ProjectName:    hi-library$
 * @Package:        com.lf.hi.hilibrary.restful$
 * @ClassName:      Filed$
 * @Author:         LF
 * @CreateDate:     2021/7/26$ 14:58$
 * @Description:
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class POST(val value: String, val formPost: Boolean = true)
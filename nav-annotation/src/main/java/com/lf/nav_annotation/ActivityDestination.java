package com.lf.nav_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.nav_annotation$
 * @ClassName: ActivityDestination$
 * @Author: LF
 * @CreateDate: 2021/7/7$ 16:13$
 * @Description:
 */
@Target(ElementType.TYPE) //标记在类、接口上
public @interface ActivityDestination {

    String pageUrl();  //url

    boolean needLogin() default false;  //是否需要登录 默认false

    boolean asStarter() default false;  //是否是首页  默认false
}

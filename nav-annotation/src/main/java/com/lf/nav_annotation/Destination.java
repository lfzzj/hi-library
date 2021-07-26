package com.lf.nav_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @ProjectName: hi-library$
 * @Package: com.lf.nav_annotation$
 * @ClassName: Destination$
 * @Author: LF
 * @CreateDate: 2021/7/7$ 9:39$
 * @Description:
 */
@Target(ElementType.TYPE) //标记在类、接口上
public @interface Destination {
    /**
     * 页面在路由中的名称
     * @return
     */
    String pageUrl();

    /**
     * 是否需要登录 默认false
     * @return
     */
    boolean needLogin() default false;

    /**
     * 是否作为路由中的第一个启动页
     * @return
     */
    boolean asStarter() default false;
}

package com.chansan.core.handler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @name: IgnoreGlobalResp
 * @author: leihuangyan
 * @classPath: com.chansan.core.handler.IgnoreGlobalResp
 * @date: 2022/12/13
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreGlobalResp {

    boolean ignore() default true;

}

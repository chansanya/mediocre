package com.chansan.common.poi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @name: ExcelExport
 * @author： LHY
 * @classPath: com.rourou.common.utils.poi.ExcelExport
 * @date: 2022/5/20 15:06
 * @Version: 1.0
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelExport {

    String title() default "系统导出";

}

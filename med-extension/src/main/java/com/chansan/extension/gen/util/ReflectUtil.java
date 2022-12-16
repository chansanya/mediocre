package com.chansan.extension.gen.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.velocity.VelocityContext;

/**
 * @name: ReflectUtil
 * @author: leihuangyan
 * @classPath: com.chansan.extension.gen.util.ReflectUtil
 * @date: 2022/12/16
 * @description: 反射帮助类
 */
public class ReflectUtil {

    /**
     *  反射获取对象的属性 注入到 context
     * @param context VelocityContext
     * @param t  需要注入的对象
     */
    public static void setContext(VelocityContext context,Object t){
        Class<?> aClass = t.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Method method = aClass.getMethod(getFieldMethodName(fieldName));
                context.put(fieldName,method.invoke(t));
            } catch (Exception e) {
                throw new RuntimeException("反射异常",e);
            }
        }
    }


    /**
     * 得到属性的 get方法名字
     * @param fieldName 属性名
     * @return 属性的get方法名
     */
    public  static  String getFieldMethodName(String fieldName){
        return String.format("get%s",toUpperCaseFirstOne(fieldName));
    }

    /**
     * 属性首字母大写
     * @param fieldName 属性名
     * @return 首字母大写的属性名
     */
    private static String toUpperCaseFirstOne(String fieldName) {
        if (Character.isUpperCase(fieldName.charAt(0))){
            return fieldName;
        }

        char firstOne = Character.toUpperCase(fieldName.charAt(0));
        String other = fieldName.substring(1);
        return firstOne + other;
    }

}

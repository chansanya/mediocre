package com.chansan.common.data;


import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class EntityUtil {


    public static <S,T> void mergeListField(List<S> sourceList, List<T> targetList)  {

        Map<Object, List<S>> sourceMap = new HashMap<>(10);


        SourceData sourceVal = null;
        for (S source : sourceList) {

            if(null == sourceVal){
                //此处 sourceVal 必不是空
                sourceVal = getSourceKeyField(source);
            }

            //得到资源Key
            Field sourceKeyField = sourceVal.getSourceKeyField();

            try {
                sourceKeyField.setAccessible(true);
                Object o = sourceKeyField.get(source);
                List<S> val = sourceMap.get(o);

                if(null== val || 0 == val.size()){
                    val = new ArrayList<>();
                }
                val.add(source);
                sourceMap.put(o,val);

            } catch (IllegalAccessException e) {
//                log.error("得到属性值异常 source:{}, field:{}", source,sourceKeyField);
                throw new RuntimeException("得到属性值异常");
            }

        }

        if(null == sourceVal){
            throw new RuntimeException("资源类未标识资源ID");
        }

        SourceId sourceIdAnnotation = sourceVal.getSourceKeyField().getAnnotation(SourceId.class);

        if(StrUtil.isBlank( sourceIdAnnotation.mark())){
            throw new RuntimeException("资源标识不能为空");
        }

        Map<String, Field> sourceFieldMap = sourceVal.getSourceVal();
        if(null == sourceFieldMap || 0 == sourceFieldMap.size()){
            //资源值属性未标识 返回
            return;
        }

        Map<Object, List<T>> targetMap = new HashMap<>(10);

        TargetData targetVal = null;
        for (T target : targetList) {
            if(null == targetVal){
                //此处 targetVal 必不是空
                targetVal = getTargetKeyField(target, sourceVal);
            }

            Field targetKeyField = targetVal.getTargetKeyField();
            try {
                //得到资源Key
                targetKeyField.setAccessible(true);
                Object o = targetKeyField.get(target);
                List<T> val = targetMap.get(o);

                if(null== val || 0 == val.size()){
                    val = new ArrayList<>();
                }
                val.add(target);
                targetMap.put(o,val);

            } catch (IllegalAccessException e) {
//                log.error("得到属性值异常 target:{}, field:{}", target,targetKeyField);
                throw new RuntimeException("得到属性值异常");
            }
        }

        log.info("资源:{}",sourceMap);
        log.info("目标:{}",targetMap);


        if(null == targetVal){
            throw new RuntimeException("目标类未标识目标ID");
        }
        MapperMethod[] mapperMethods = targetVal.getMapperMethods();

        log.info("准备转换:{}",Arrays.asList(mapperMethods));

        mergeListField(sourceMap,targetMap,mapperMethods);
    }


    @Data
    static  class SourceData{
        private  Field sourceKeyField;
        private  Map<String, Field> sourceVal;
    }

    @Data
    static  class TargetData{
        private  Field targetKeyField;
        private  MapperMethod[] mapperMethods;
    }


    private static <S>  SourceData getSourceKeyField(S source) {
        Class<?> sourceClass = source.getClass();

        Field[] fields = sourceClass.getDeclaredFields();
        if(0 == fields.length){
            throw new RuntimeException("资源类不能为空");
        }

        SourceData returnSource = new SourceData();

        HashMap<String, Field> sourceValField = new HashMap<>(fields.length);
        for (Field field : fields) {

            //是否有 SourceVal
            SourceVal sourceValAnnotation = field.getAnnotation(SourceVal.class);
            if(null != sourceValAnnotation){
                //如果注解未设置值 ，设置属性名
                sourceValField.put(StrUtil.isBlank(sourceValAnnotation.value())?field.getName():sourceValAnnotation.value(),field);
            }

            //是否有 SourceId
            SourceId sourceIdAnnotation = field.getAnnotation(SourceId.class);
            if(null !=  sourceIdAnnotation){
                returnSource.setSourceKeyField(field);
            }

        }
        if(null == returnSource.getSourceKeyField()){
            throw new RuntimeException("资源数据必须标识@SourceId");
        }

        returnSource.setSourceVal(sourceValField);
        return returnSource;

    }

    private static <T> TargetData getTargetKeyField(T target, SourceData sourceVal) {
        TargetData returnTarget = new TargetData();

        List<MapperMethod> mapperMethods = new ArrayList<>();

        Map<String, Field> sourceFieldMap = sourceVal.getSourceVal();
        Set<String> sourceFieldKey = sourceFieldMap.keySet();

        Class<?> targetClass = target.getClass();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            //是否有 TargetId

            TargetVal targetValAnnotation = field.getAnnotation(TargetVal.class);


            if(null !=  targetValAnnotation ){
                String targetValName =  StrUtil.isBlank(targetValAnnotation.value())?field.getName():targetValAnnotation.value();

                if(sourceFieldKey.contains(targetValName)){
                    //如果注解未设置值 ，设置属性名
                    Field sourceField = sourceFieldMap.get(targetValName);
                    if(null != sourceField){
                        mapperMethods.add(new MapperMethod(fieldToGetter(sourceField),fieldToGetter(field)));
                    }
                }

            }

            TargetId targetIdAnnotation = field.getAnnotation(TargetId.class);
            SourceId sourceIdAnnotation = sourceVal.getSourceKeyField().getAnnotation(SourceId.class);

            if(null !=  targetIdAnnotation && targetIdAnnotation.mark().equals(sourceIdAnnotation.mark())){
                returnTarget.setTargetKeyField(field);
            }
        }

        if(null == returnTarget.getTargetKeyField()){
            throw new RuntimeException("资源数据必须标识@SourceId");
        }

        returnTarget.setMapperMethods(mapperMethods.toArray(new MapperMethod[0]));

        return returnTarget;

    }



    /**
     *合并集合属性
     * @param sourceList 资源list
     * @param targetList 目标List
     * @param sourceMarkField 资源标记属性
     * @param targetMarkField 目标标记属性
     * @param <S> 资源对象
     * @param <T> 目标对象
     */
    public static <S,T> void mergeListField(List<S> sourceList, List<T> targetList,IGetter<S> sourceMarkField,IGetter<T> targetMarkField, MapperField... mapperField)  {
        //得到资源属性
        log.info("源资源:{}",sourceList);
        log.info("目标资源:{}",targetList);

        Map<Object, List<S>> sourceMap = sourceList.stream().filter(i -> null != sourceMarkField.get(i)).collect(Collectors.groupingBy(sourceMarkField::get));
        Map<Object, List<T>> targetMap = targetList.stream().filter(i -> null != targetMarkField.get(i)).collect(Collectors.groupingBy(targetMarkField::get));


        MapperMethod [] mapperMethods = new MapperMethod[mapperField.length];

        for (int i = 0; i < mapperField.length; i++) {
            MapperField  field  = mapperField[i];
            String sourceMethodName;
            try {
                sourceMethodName = convertToMethodName(field.getSourceField());
            } catch (Exception e) {
                log.error("未找到资源属性名", e);
                throw new RuntimeException("未找到资源属性名");
            }

            String targetMethodName;
            try {
                targetMethodName = convertToMethodName(field.getTargetField());
            } catch (Exception e) {
                log.error("未找到目标属性名", e);
                throw new RuntimeException("未找到目标属性名");
            }
            mapperMethods [i] = new MapperMethod(sourceMethodName,targetMethodName);
        }

        mergeListField(sourceMap,targetMap,mapperMethods);
    }


    /**
     *合并集合属性
     * @param sourceMap 资源 Map key:资源唯一标识  val:资源数据
     * @param targetMap 目标 Map key:资源唯一标识  val:目标数据
     * @param <S> 资源对象
     * @param <T> 目标对象
     */
    private static <S,T> void mergeListField(Map<Object, List<S>> sourceMap, Map<Object, List<T>> targetMap, MapperMethod... mapperMethods)  {

        for (Map.Entry<Object, List<S>> entry : sourceMap.entrySet()) {
            //得到当前资源 标识KEY
            Object sourceKey = entry.getKey();

            //得到所有目标 标识Key
            Set<Object> targetMapKeys = targetMap.keySet();
            if(!targetMapKeys.contains(sourceKey)){
                //不匹配 continue 匹配下一个资源
                continue;
            }
            //匹配成功得到资源值
            List<S> sourceObgList = entry.getValue();
            //理论上资源标识key应保持唯一 所以这里取第一个
            S source = sourceObgList.get(0);

            //遍历 与资源数据标识key 匹配的 目标内容 并遍历
            for(T target:targetMap.get(sourceKey)){
                //log.info("需要设置值目标class:{}",target);

                for (MapperMethod mapperMethod : mapperMethods) {
                    Object sourceVal = ReflectUtil.invoke(source, mapperMethod.getSourceMethodName());
                    //log.info("资源方法名：{} 属性值->{}",mapperMethod.getSourceMethodName(), sourceVal);

                    String targetSetMethodName = convertToSetter(mapperMethod.getTargetMethodName());
                    Object targetVal = ReflectUtil.invoke(target, targetSetMethodName, sourceVal);
                    log.info("目标方法名：{} 属性值->{}",targetSetMethodName,targetVal);

                }//遍历映射方法结束
            }// 遍历 与源数据key 匹配的目标内容结束
        }//遍历源数据结束
        //程序结束
    }




    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public  @interface  SourceId  {
        String mark();
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public  @interface  TargetId  {
        String mark();
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public  @interface  SourceVal  {
        String value() default "";
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public  @interface  TargetVal  {
        String value() default "";
    }


    @Data
    @AllArgsConstructor
    public  static class MapperField{

        private IGetter<?> sourceField;
        private  IGetter<?> targetField;

        public static <S,T> MapperField gen(IGetter<S> sourceField,IGetter<T> targetField){
            return new MapperField(sourceField,targetField);
        }

        public static <S> MapperField gen(IGetter<S> sourceField){
            return new MapperField(sourceField,sourceField);
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public  static class MapperMethod{

        private String sourceMethodName;
        private String targetMethodName;


        public static  MapperMethod gen(String sourceMethodName, String targetMethodName) {
            return new MapperMethod(sourceMethodName,targetMethodName);
        }

        public static  MapperMethod gen(String sourceMethodName) {
            return new MapperMethod(sourceMethodName,sourceMethodName);
        }


    }




    @FunctionalInterface
    public interface IGetter<T>  extends Serializable  {
        /**
         *  对 source 进行操作 得到 obg
         * @param source 资源
         * @return obg
         */
        Object get(T source);
    }




    /**
     * 转换成方法名
     */
    private static <T> String convertToMethodName(IGetter<T> fn) throws Exception {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        if (!methodName.startsWith("get")) {
            System.out.println("无效的setter方法：" + methodName);
        }
        return toLowerCaseFirstOne(methodName.replace("set", ""));
    }


    /**
     * 关键在于这个方法
     */
    private static SerializedLambda getSerializedLambda(Serializable fn) throws Exception {
        Method method = fn.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        return  (SerializedLambda) method.invoke(fn);
    }


    /**
     * 装换为Get
     */
    private static String fieldToGetter(Field field) {
        return "get" +  toUpperCaseFirstOne(field.getName());
    }

    /**
     * 装换为Set
     */
    private static String convertToSetter(String methodName) {
        return "s" +  methodName.substring(1);
    }

    /**
     * 字符串首字母转小写
     */
    private static String toLowerCaseFirstOne(String field) {
        if (Character.isLowerCase(field.charAt(0))){
            return field;
        } else {
            char firstOne = Character.toLowerCase(field.charAt(0));
            String other = field.substring(1);
            return firstOne + other;
        }
    }

    /**
     * 字符串首字母转大写
     */
    private static String toUpperCaseFirstOne(String field) {
        if (Character.isUpperCase(field.charAt(0))){
            return field;
        } else {
            char firstOne = Character.toUpperCase(field.charAt(0));
            String other = field.substring(1);
            return firstOne + other;
        }
    }




}


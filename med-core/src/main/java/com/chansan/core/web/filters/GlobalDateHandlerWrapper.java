package com.chansan.core.web.filters;


import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * @author yf
 */
public class GlobalDateHandlerWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> parameterMap;

    public GlobalDateHandlerWrapper(HttpServletRequest request) {
        super(request);
        parameterMap = request.getParameterMap();
    }

    /**
     * 获取所有参数名
     * @return 返回所有参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<>(parameterMap.keySet());
        return vector.elements();
    }

    /**
     * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
     * @param name 指定参数名
     * @return 指定参数名的值
     */
    @Override
    public String getParameter(String name) {
        return  super.getParameter(name);
    }


    /**
     * 获取指定参数名的所有值的数组，如：checkbox的所有数据
     * 接收数组变量
     */
    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }


}

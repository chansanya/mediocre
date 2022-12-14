package com.chansan.core.web.filters;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @name: PageRequestFilter
 * @author: leihuangyan
 * @classPath: com.chansan.core.web.filters.PageRequestFilter
 * @date: 2022/12/14
 * @description:
 */
@Slf4j
@Component
public class PageRequestFilter extends OncePerRequestFilter {


    private static final String CURRENT = "current";
    private static final String SIZE = "size";
    private static final String PAGE_NUM = "pageNum";
    private static final String PAGE_SIZE = "pageSize";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 获取所有参数集合
        Map<String, String[]> parameterMap = new HashMap<>(request.getParameterMap());

        //打印请求信息
//        printRequestInfo(request);

        if (0 == parameterMap.size()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (parameterMap.containsKey(CURRENT) && parameterMap.containsKey(SIZE)) {
            //同时包含  current 和 size
            filterChain.doFilter(request, response);
            return;
        }

        if (!(parameterMap.containsKey(PAGE_NUM) && parameterMap.containsKey(PAGE_SIZE))) {
            // 既没有 pageNum  也没有 pageSize
            filterChain.doFilter(request, response);
            return;
        }

        parameterMap.put(CURRENT, parameterMap.get(PAGE_NUM));
        parameterMap.put(SIZE, parameterMap.get(PAGE_SIZE));
        parameterMap.remove(PAGE_NUM);
        parameterMap.remove(PAGE_SIZE);

        var globalDateHandlerWrapper = new GlobalDateHandlerWrapper(request);
        // 将集合存到自定义HttpServletRequestWrapper
        globalDateHandlerWrapper.setParameterMap(parameterMap);

        // 替换原本的request
        filterChain.doFilter(globalDateHandlerWrapper, response);
    }




}

package com.chansan.common.utils;


import java.io.IOException;

import com.chansan.common.exception.UtilException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @name: Jacksonutil
 * @author: leihuangyan
 * @classPath: com.chansan.common.utils.Jacksonutil
 * @date: 2022/12/14
 * @description:
 */
public class JacksonUtil {
    private final static ObjectMapper OBJECT_MAPPER;
    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static void  writeValue(HttpServletResponse response,String content){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            OBJECT_MAPPER.writeValue(response.getOutputStream(),content);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }

}

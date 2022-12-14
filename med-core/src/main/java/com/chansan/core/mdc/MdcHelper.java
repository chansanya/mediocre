package com.chansan.core.mdc;

import org.slf4j.MDC;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Administrator
 */
@Slf4j
public class MdcHelper {

    private final static String REQ_ID = "REQ_ID";

    private final static String BUSINESS = "BUSINESS";


    public static void  putReqId(HttpServletRequest request){
        try {
            MDC.put(REQ_ID,request.getSession(true).getId());
        } catch (IllegalArgumentException e) {
            log.warn("获取 SessionId 失败");
        }
    }

    public static void  put(String key,String val){
        MDC.put(key,val);
    }

    public static void  put(String val){
        MDC.put(BUSINESS,val);
    }


    public static String  get(){
        return MDC.get(BUSINESS);
    }

    public static void  remove(){
        MDC.remove(BUSINESS);
    }
}

package com.chansan.common.utils;

import java.util.Optional;

/**
 * @author yf
 */
public class ApplicationContext {

    public final static  String APPLICATION_ID = "Application-Id";

    private final  static  ThreadLocal<Long> APP_THREAD_LOAD =  new ThreadLocal<>();

    public static void set(Long applicationId){
        APP_THREAD_LOAD.set(applicationId);
    }
    public static Long get(){
        return Optional.ofNullable(APP_THREAD_LOAD.get()).orElse(0L);
    }

    public static void remove(){
        APP_THREAD_LOAD.remove();
    }


    public static boolean isAdmin(Long userId) {
        if(null == userId){
            return false;
        }
        long applicationId = get();
        if (applicationId == 0) {
            return 1L == userId;
        }
        return false;
    }

    public static boolean isAdminRole(Long roleId) {
        if(null == roleId){
            return false;
        }
        long applicationId = get();
        if (applicationId == 0) {
            return 1L == roleId;
        }
        return false;
    }

}

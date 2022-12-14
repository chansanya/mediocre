package com.chansan.core.web.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chansan.common.base.resp.AjaxResult;
import com.chansan.common.base.resp.RespStatus;
import com.chansan.common.exception.DemoModeException;
import com.chansan.common.exception.ServiceException;
import com.chansan.common.exception.UtilException;

/**
 * 全局异常处理器
 *
 * @author rourou
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error("系统异常",e);
        return convertToReturn(e);
    }

    /**
     * 错误转换为返回对象
     *
     * @param exception exception
     * @return AjaxResult
     */
    public static AjaxResult convertToReturn(Throwable exception) {
        if (exception == null) {
            return AjaxResult.error("系统异常请联系管理员");
        }

        log.error("异常信息: " , exception);

        if (exception instanceof MissingServletRequestParameterException) {
            return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), "缺少请求参数");

        } else if (exception instanceof NullPointerException) {
            return AjaxResult.error(RespStatus.ERROR, "空指针异常");

        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            return AjaxResult.error(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持请求方式");

        } else if (exception instanceof  DuplicateKeyException){
            return AjaxResult.error("信息已存在");

        } else if (exception instanceof  BadSqlGrammarException){
            return AjaxResult.error("异常SQL语句");

        }  else if (exception instanceof DemoModeException){
            return AjaxResult.error("演示模式，不允许操作");

        } else if (exception instanceof  MethodArgumentNotValidException){
            return AjaxResult.error("自定义验证异常");

        } else if (exception instanceof  BindException bindException){
            String message = bindException.getAllErrors().get(0).getDefaultMessage();
            return AjaxResult.error(message);

        }
//        else if (exception instanceof  AccessDeniedException){
//            return AjaxResult.error(HttpStatus.UNAUTHORIZED.value(), "没有权限，请联系管理员授权");
//        }

        else if (exception instanceof ServiceException serviceException){
            var code =   serviceException.getCode();
            var message = serviceException.getMessage();
            return ObjectUtils.isEmpty(code) ? AjaxResult.error(message): AjaxResult.error(code,message);

        }else if (exception instanceof UtilException utilException) {
            var message = utilException.getMessage();
            Throwable cause = utilException.getCause();
            return ObjectUtils.isEmpty(cause) ?AjaxResult.error(message):AjaxResult.error(cause.getMessage());
        }

        return AjaxResult.error("未知异常,请联系管理员");
    }
}

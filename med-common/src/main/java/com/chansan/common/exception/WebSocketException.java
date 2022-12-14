package com.chansan.common.exception;

/**
 * WebSocketException
 *
 * @author yf
 */
public final class WebSocketException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public WebSocketException() {
    }

    public WebSocketException(String message) {
        this.message = message;
    }

    public WebSocketException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public WebSocketException setMessage(String message) {
        this.message = message;
        return this;
    }

    public WebSocketException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
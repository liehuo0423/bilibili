package com.bilibili.domain.exception;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public class ConditionException extends RuntimeException {
    private final static Long serialVersionUID = 1L;
    private Integer code;

    public ConditionException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ConditionException(String message) {
        super(message);
        this.code = 500;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

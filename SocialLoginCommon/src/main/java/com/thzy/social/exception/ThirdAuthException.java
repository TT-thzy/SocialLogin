package com.thzy.social.exception;

/**
 * @className: ThirdAuthException
 * @description:
 * @author: TT-Berg
 * @date: 2023/6/29
 **/
public class ThirdAuthException extends RuntimeException {

    private String errorCode;

    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

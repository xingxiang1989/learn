package com.some.http;

import java.io.IOException;

/**
 * @author xiangxing
 */
public class ApiException extends IOException {

    private int resultCode;
    private String message;

    public ApiException(int resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
        this.message = message;
    }
}

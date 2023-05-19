package com.xuanwenchao.metaphor.exception;

/**
 * @Package com.xuanwenchao.metaphor.exception
 * @Description: base exception class in metaphor project
 * @author Xuan Wenchao
 * @date Dec 02,2022
 */

public class MetaphorException extends RuntimeException {
    public MetaphorException(String msg) {
        super(msg);
    }
}

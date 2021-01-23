/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagarro.s4m.Exceptions;

/**
 *
 * @author david
 */
class ApiError {
    
    private short code;
    private String msg;

    public ApiError(short code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public short getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    
    
    
}

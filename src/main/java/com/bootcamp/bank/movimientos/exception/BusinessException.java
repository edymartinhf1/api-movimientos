package com.bootcamp.bank.movimientos.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String messageError) {
        super(messageError);
    }


}
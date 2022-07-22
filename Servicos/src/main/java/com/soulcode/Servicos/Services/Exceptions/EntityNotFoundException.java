package com.soulcode.Servicos.Services.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    //criando um constructor
    public EntityNotFoundException (String msg) {
        super(msg);
    }
}

package com.yermilov.exception;

public class DAOException extends Exception {
    public DAOException(){
        super();
    }

    public DAOException(String message){
        super(message);
    }
}

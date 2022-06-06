package ru.itmo.kotiki.tool;

public class ServiceException extends Exception{

    public ServiceException(){}

    public ServiceException(String message){
        super(message);
    }
}

package ru.fateyev.test_task.util;

public class ResourceNotCreatedException extends RuntimeException{
    public ResourceNotCreatedException(String message){
        super(message);
    }
}

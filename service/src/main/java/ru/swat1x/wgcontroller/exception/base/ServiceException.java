package ru.swat1x.wgcontroller.exception.base;

import lombok.experimental.StandardException;

@StandardException
public class ServiceException extends RuntimeException {

    private static final String BASE_MESSAGE =  "Something went wrong (Need to override)";

    public ServiceException() {
        this(BASE_MESSAGE);
    }

    public ServiceException(Throwable cause) {
        super(BASE_MESSAGE, cause);
    }

}

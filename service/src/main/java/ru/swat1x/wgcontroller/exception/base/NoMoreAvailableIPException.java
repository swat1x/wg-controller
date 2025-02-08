package ru.swat1x.wgcontroller.exception.base;

import org.springframework.http.HttpStatus;

@ResponseException(HttpStatus.CONFLICT)
public class NoMoreAvailableIPException extends ServiceException {

    public NoMoreAvailableIPException() {
        super("No more available IP");
    }

}

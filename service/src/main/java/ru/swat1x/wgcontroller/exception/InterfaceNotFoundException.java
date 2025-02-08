package ru.swat1x.wgcontroller.exception;

import org.springframework.http.HttpStatus;
import ru.swat1x.wgcontroller.exception.base.ResponseException;
import ru.swat1x.wgcontroller.exception.base.ServiceException;

@ResponseException(HttpStatus.NOT_FOUND)
public class InterfaceNotFoundException extends ServiceException {

    public InterfaceNotFoundException(String configurationName) {
        super("Interface not found: " + configurationName);
    }
}

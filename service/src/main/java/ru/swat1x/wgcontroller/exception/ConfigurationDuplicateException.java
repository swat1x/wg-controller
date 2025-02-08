package ru.swat1x.wgcontroller.exception;

import org.springframework.http.HttpStatus;
import ru.swat1x.wgcontroller.exception.base.ResponseException;
import ru.swat1x.wgcontroller.exception.base.ServiceException;

@ResponseException(HttpStatus.NOT_FOUND)
public class ConfigurationDuplicateException extends ServiceException {

    public ConfigurationDuplicateException(String configurationName) {
        super("Конфигурация с названием \"%s\" уже существует".formatted(configurationName));
    }
}

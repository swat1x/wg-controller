package ru.swat1x.wgcontroller.exception.base;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.swat1x.wgcontroller.exception.dto.ExceptionResponseDTO;

@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionResponseDTO handle(Exception exception,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        var isServiceException = exception instanceof ServiceException;
        var responseException = exception.getClass().getAnnotation(ResponseException.class);

        var message = exception.getMessage();
        var httpStatus = responseException == null ?
                (isServiceException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR) :
                responseException.value();

        response.setStatus(httpStatus.value());

        if (!isServiceException) {
            log.error("Handled exception: {}", message, exception);
        }

        return new ExceptionResponseDTO(message);
    }


}

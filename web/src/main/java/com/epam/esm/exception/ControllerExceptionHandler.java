package com.epam.esm.exception;

import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<HttpExceptionResponse> handleServiceException(HttpServletRequest httpServletRequest, ServiceException e) {
        String errorMessage = messageSource.getMessage(e.getErrorCode(), e.getArgs(), getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(e.getErrorCode(), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<HttpExceptionResponse> handleRepositoryException(HttpServletRequest httpServletRequest, RepositoryException e) {
        String errorMessage = messageSource.getMessage(e.getErrorCode(), e.getArgs(), getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(e.getErrorCode(), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpExceptionResponse handleValidationExceptions(
            HttpServletRequest httpServletRequest, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().get(0);
        String errorCode = error.getDefaultMessage();
        String message = messageSource.getMessage(errorCode, null, getLocale(httpServletRequest));
        return new HttpExceptionResponse(errorCode, message);
    }

    private Locale getLocale(HttpServletRequest httpServletRequest) {
        String localeString = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return localeString != null ? Locale.forLanguageTag(localeString) : Locale.ENGLISH;
    }
}

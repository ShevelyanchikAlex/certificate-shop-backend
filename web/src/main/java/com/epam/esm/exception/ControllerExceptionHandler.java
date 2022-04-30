package com.epam.esm.exception;

import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {
    public static final String BAD_REQUEST_STATUS_CODE = "40000";
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpExceptionResponse> badRequestError(HttpServletRequest httpServletRequest, Exception e) {
        String errorMessage = messageSource.getMessage(BAD_REQUEST_STATUS_CODE, new Object[]{}, getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(BAD_REQUEST_STATUS_CODE, errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    private Locale getLocale(HttpServletRequest httpServletRequest) {
        String localeString = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return localeString != null ? Locale.forLanguageTag(localeString) : Locale.ENGLISH;
    }
}

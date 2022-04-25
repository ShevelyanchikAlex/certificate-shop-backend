package com.epam.esm.exception;

import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<HttpExceptionResponse> handleServiceException(ServiceException e) {
        String errorMessage = messageSource.getMessage(e.getErrorCode(), e.getArgs(), Locale.ENGLISH);
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(e.getErrorCode(), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<HttpExceptionResponse> handleRepositoryException(RepositoryException e) {
        String errorMessage = messageSource.getMessage(e.getErrorCode(), e.getArgs(), Locale.ENGLISH);
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(e.getErrorCode(), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpExceptionResponse handleValidationExceptions(
            MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().get(0);
        String errorCode = error.getDefaultMessage();
        String message = messageSource.getMessage(errorCode, null, Locale.ENGLISH);
        return new HttpExceptionResponse(errorCode, message);
    }
}

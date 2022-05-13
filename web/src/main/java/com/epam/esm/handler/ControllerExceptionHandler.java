package com.epam.esm.handler;

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
    private static final String BAD_REQUEST_MESSAGE_KEY = "bad.request";
    private static final String OPERATION_NOT_SUPPORTED_MESSAGE_KEY = "operation.not.supported";
    private static final String GIFT_CERTIFICATE_NOT_FOUND_MESSAGE_KEY = "gift.certificate.not.found";
    private static final String TAG_NOT_FOUND_MESSAGE_KEY = "tag.not.found";
    private static final String RESOURCE_ALREADY_EXIST_MESSAGE_KEY = "resource.already.exist";
    private static final String REQUEST_VALIDATE_ERROR_MESSAGE_KEY = "request.validate.error";
    private static final String GIFT_CERTIFICATE_VALIDATE_MESSAGE_KEY = "gift.certificate.validate.error";
    private static final String TAG_VALIDATE_ERROR_MESSAGE_KEY = "tag.validate.error";
    private static final String FILTER_CONDITION_VALIDATE_MESSAGE_KEY = "gift.certificate.filter.condition.validate.error";
    private static final String UPDATE_CONDITION_VALIDATE_MESSAGE_KEY = "gift.certificate.update.condition.error";

    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<HttpExceptionResponse> handleServiceException(HttpServletRequest httpServletRequest, ServiceException e) {
        String errorMessage = messageSource.getMessage(e.getMessage(), e.getArgs(), getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(getErrorCodeByMessageKey(e.getMessage()), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<HttpExceptionResponse> handleRepositoryException(HttpServletRequest httpServletRequest, RepositoryException e) {
        String errorMessage = messageSource.getMessage(e.getMessage(), e.getArgs(), getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(getErrorCodeByMessageKey(e.getMessage()), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpExceptionResponse> badRequestError(HttpServletRequest httpServletRequest, Exception e) {
        String errorMessage = messageSource.getMessage(BAD_REQUEST_MESSAGE_KEY, new Object[]{}, getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(HttpErrorCode.BAD_REQUEST_CODE, errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    private Locale getLocale(HttpServletRequest httpServletRequest) {
        String localeString = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return localeString != null ? Locale.forLanguageTag(localeString) : Locale.ENGLISH;
    }

    private String getErrorCodeByMessageKey(String messageKey) {
        return switch (messageKey) {
            case OPERATION_NOT_SUPPORTED_MESSAGE_KEY -> HttpErrorCode.OPERATION_NOT_SUPPORTED_CODE;
            case GIFT_CERTIFICATE_NOT_FOUND_MESSAGE_KEY -> HttpErrorCode.GIFT_CERTIFICATE_NOT_FOUND_CODE;
            case TAG_NOT_FOUND_MESSAGE_KEY -> HttpErrorCode.TAG_NOT_FOUND_CODE;
            case RESOURCE_ALREADY_EXIST_MESSAGE_KEY -> HttpErrorCode.RESOURCE_ALREADY_EXIST_CODE;
            case REQUEST_VALIDATE_ERROR_MESSAGE_KEY -> HttpErrorCode.REQUEST_VALIDATE_ERROR_CODE;
            case GIFT_CERTIFICATE_VALIDATE_MESSAGE_KEY -> HttpErrorCode.GIFT_CERTIFICATE_VALIDATE_ERROR_CODE;
            case TAG_VALIDATE_ERROR_MESSAGE_KEY -> HttpErrorCode.TAG_VALIDATE_ERROR_CODE;
            case FILTER_CONDITION_VALIDATE_MESSAGE_KEY -> HttpErrorCode.FILTER_CONDITION_VALIDATE_ERROR_CODE;
            case UPDATE_CONDITION_VALIDATE_MESSAGE_KEY -> HttpErrorCode.UPDATE_CONDITION_VALIDATE_ERROR_CODE;
            default -> HttpErrorCode.BAD_REQUEST_CODE;
        };
    }
}

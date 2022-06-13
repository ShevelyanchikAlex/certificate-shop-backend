package com.epam.esm.handler;

import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.service.exception.JwtAuthenticationException;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final String BAD_REQUEST_MESSAGE_KEY = "bad.request";
    private static final String FORBIDDEN_MESSAGE_KEY = "operation.forbidden";
    private final Map<String, String> exceptionCodeMap;
    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.exceptionCodeMap = initExceptionCodeMap();
    }

    private Map<String, String> initExceptionCodeMap() {
        Map<String, String> codeMap = new ConcurrentHashMap<>();
        codeMap.put("operation.not.supported", HttpErrorCode.OPERATION_NOT_SUPPORTED_CODE);
        codeMap.put("gift.certificate.not.found", HttpErrorCode.GIFT_CERTIFICATE_NOT_FOUND_CODE);
        codeMap.put("tag.not.found", HttpErrorCode.TAG_NOT_FOUND_CODE);
        codeMap.put("resource.already.exist", HttpErrorCode.RESOURCE_ALREADY_EXIST_CODE);
        codeMap.put("request.validate.error", HttpErrorCode.REQUEST_VALIDATE_ERROR_CODE);
        codeMap.put("gift.certificate.validate.error", HttpErrorCode.GIFT_CERTIFICATE_VALIDATE_ERROR_CODE);
        codeMap.put("tag.validate.error", HttpErrorCode.TAG_VALIDATE_ERROR_CODE);
        codeMap.put("gift.certificate.update.condition.error", HttpErrorCode.UPDATE_CONDITION_VALIDATE_ERROR_CODE);
        codeMap.put("gift.certificate.filter.condition.validate.error", HttpErrorCode.FILTER_CONDITION_VALIDATE_ERROR_CODE);
        codeMap.put("order.not.found", HttpErrorCode.ORDER_NOT_FOUND_CODE);
        codeMap.put("user.not.found", HttpErrorCode.USER_NOT_FOUND_CODE);
        codeMap.put("user.validate.error", HttpErrorCode.USER_VALIDATE_ERROR_CODE);
        codeMap.put(BAD_REQUEST_MESSAGE_KEY, HttpErrorCode.BAD_REQUEST_CODE);
        codeMap.put(FORBIDDEN_MESSAGE_KEY, HttpErrorCode.FORBIDDEN_CODE);
        return codeMap;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<HttpExceptionResponse> handleServiceException(HttpServletRequest httpServletRequest, ServiceException e) {
        String errorMessage = messageSource.getMessage(e.getMessage(), e.getArgs(), getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(exceptionCodeMap.get(e.getMessage()), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<HttpExceptionResponse> handleRepositoryException(HttpServletRequest httpServletRequest, RepositoryException e) {
        String errorMessage = messageSource.getMessage(e.getMessage(), e.getArgs(), getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(exceptionCodeMap.get(e.getMessage()), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpExceptionResponse> badRequestError(HttpServletRequest httpServletRequest, Exception e) {
        String errorMessage = messageSource.getMessage(BAD_REQUEST_MESSAGE_KEY, new Object[]{}, getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(HttpErrorCode.BAD_REQUEST_CODE, errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<HttpExceptionResponse> forbiddenError(HttpServletRequest httpServletRequest, Exception e) {
        String errorMessage = messageSource.getMessage(FORBIDDEN_MESSAGE_KEY, new Object[]{}, getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(HttpErrorCode.FORBIDDEN_CODE, errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<HttpExceptionResponse> jwtAuthenticationError(HttpServletRequest httpServletRequest, Exception e) {
        String errorMessage = messageSource.getMessage(e.getMessage(), new Object[]{}, getLocale(httpServletRequest));
        HttpExceptionResponse errorResponse = new HttpExceptionResponse(exceptionCodeMap.get(e.getMessage()), errorMessage);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    private Locale getLocale(HttpServletRequest httpServletRequest) {
        String localeString = httpServletRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return localeString != null ? Locale.forLanguageTag(localeString) : Locale.ENGLISH;
    }
}

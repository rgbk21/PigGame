package com.rgbk21.spring;

import com.rgbk21.utils.CommonUtils;
import com.rgbk21.utils.ErrorInfo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler {

    public GameExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // HttpStatus status = HttpStatus.BAD_REQUEST; // 400 Bad Request.
        return handleExceptionInternal(ex, createExceptionBodyMsg(ex), headers, status, request);
    }

    private ErrorInfo createExceptionBodyMsg(HttpMessageNotReadableException ex) {
        String errorCode = ex.getClass().getSimpleName();
        String errorMessage6 = ExceptionUtils.getMessage(ex);
        String errorMessage7 = ExceptionUtils.getRootCauseMessage(ex);
        String errorMessage5 = ex.toString();
        String errorMessage2 = ex.getMessage();
        String errorMessage4 = ex.getLocalizedMessage();
        String errorMessage3 = Objects.requireNonNull(ex.getCause()).getMessage();
        String errorMessage = Objects.requireNonNull(ex.getRootCause()).getMessage();

        return CommonUtils.createErrorInfo(errorCode, errorMessage);
    }
}

package com.marcinski.complaintcommand.api.exceptionhandler;

import com.marcinski.complaintcommand.api.dto.ApiError;
import com.marcinski.complaintcommand.domain.exceptions.AggregateNotFoundException;
import com.marcinski.complaintcommand.domain.exceptions.ConcurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AggregateNotFoundException.class)
    public ResponseEntity<ApiError> on(AggregateNotFoundException ex) {
        log.warn(ex.getMessage());
        var apiError = ApiError.of(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConcurrencyException.class)
    public ResponseEntity<ApiError> on(ConcurrencyException ex) {
        log.error(ex.getMessage());
        var apiError = ApiError.of(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> on(Exception ex) {
        log.error(ex.getMessage());
        var apiError = ApiError.of(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<String> validationMessages = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        var apiError = ApiError.of("Wrong body", validationMessages);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}

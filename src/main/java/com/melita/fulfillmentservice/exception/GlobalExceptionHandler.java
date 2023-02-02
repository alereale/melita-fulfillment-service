package com.melita.fulfillmentservice.exception;

import com.melita.fulfillmentservice.exception.response.ErrorDetails;
import com.melita.fulfillmentservice.exception.response.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Locale;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        log.error(ex);
        return new ResponseEntity<>("Exception occur " + ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        log.error(ex);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, Locale locale) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, Locale locale) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}

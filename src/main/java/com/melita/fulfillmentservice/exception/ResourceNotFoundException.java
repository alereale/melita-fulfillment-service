package com.melita.fulfillmentservice.exception;

import com.melita.fulfillmentservice.exception.response.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends GlobalException {

    public ResourceNotFoundException() {
        super("Resource not found", ErrorCode.NOT_FOUND);
    }

    public ResourceNotFoundException(String message){
        super(message, ErrorCode.NOT_FOUND);
    }

    public ResourceNotFoundException(String message, String code) {
        super(message, code);
    }

//    private ErrorResponse errorResponse;
//
//
//    public ResourceNotFoundException(final ErrorResponse errorResponse) {
//        super(errorResponse.getMessage());
//        this.errorResponse = errorResponse;
//    }

//    public static ResourceNotFoundException notFound(final String message, final Object... args) {
//        return new ResourceNotFoundException(
//                new ErrorResponse(HttpStatus.NOT_FOUND, MessageFormat.format(message, args))
//        );
//    }
}

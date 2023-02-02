package com.melita.fulfillmentservice.exception;

import com.melita.fulfillmentservice.exception.response.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends GlobalException {
    public BadRequestException() {
        super("Bad request", ErrorCode.BAD_REQUEST);
    }

    public BadRequestException(String message){
        super(message, ErrorCode.BAD_REQUEST);
    }

    public BadRequestException(String message, String code) {
        super(message, code);
    }

}

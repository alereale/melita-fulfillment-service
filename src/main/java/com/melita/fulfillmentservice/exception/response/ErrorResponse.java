package com.melita.fulfillmentservice.exception.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class ErrorResponse {
    private String code;

    private String message;
}

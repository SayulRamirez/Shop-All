package com.metaphorce.shop_all.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class ExceptionResponse {

    private String message;

    private HttpStatus status;

    private ZonedDateTime timestamp;
}

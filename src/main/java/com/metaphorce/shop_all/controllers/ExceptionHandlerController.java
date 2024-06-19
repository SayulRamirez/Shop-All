package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.ExceptionResponse;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEntityExistsException(EntityExistsException exception) {

        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .timestamp(ZonedDateTime.now(ZoneId.of("America/Chicago"))).build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}

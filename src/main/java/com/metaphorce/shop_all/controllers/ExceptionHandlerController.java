package com.metaphorce.shop_all.controllers;

import com.metaphorce.shop_all.domain.ExceptionResponse;
import com.metaphorce.shop_all.exceptions.NotEnoughStockException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, Object> response = new LinkedHashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            response.put(field, message);
        });

        response.put("timestamp", ZonedDateTime.now(ZoneId.of("America/Chicago")));
        response.put("status", HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionResponse> handleEntityExistsException(EntityExistsException exception) {

        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .timestamp(ZonedDateTime.now(ZoneId.of("America/Chicago"))).build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(ZonedDateTime.now(ZoneId.of("America/Chicago"))).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ExceptionResponse> handleNotEnoughStock(NotEnoughStockException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .timestamp(ZonedDateTime.now(ZoneId.of("America/Chicago"))).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

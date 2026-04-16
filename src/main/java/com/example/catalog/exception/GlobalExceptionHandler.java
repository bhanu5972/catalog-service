package com.example.catalog.exception;

import com.example.catalog.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    private String getCorrelationId(WebRequest request) {
        String correlationId = request.getHeader("X-Correlation-Id");
        return correlationId != null ? correlationId : UUID.randomUUID().toString();
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex, WebRequest request) {
        
        ErrorResponse error = new ErrorResponse();
        error.setCode("PRODUCT_NOT_FOUND");
        error.setMessage(ex.getMessage());
        error.setCorrelationId(getCorrelationId(request));
        error.setTimestamp(Instant.now());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        error.setStatus(HttpStatus.NOT_FOUND.value());
        
        log.warn("Product not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ErrorResponse error = new ErrorResponse();
        error.setCode("VALIDATION_ERROR");
        error.setMessage("Validation failed: " + errors.toString());
        error.setCorrelationId(getCorrelationId(request));
        error.setTimestamp(Instant.now());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        log.error("Unexpected error occurred", ex);
        
        ErrorResponse error = new ErrorResponse();
        error.setCode("INTERNAL_SERVER_ERROR");
        error.setMessage("An unexpected error occurred");
        error.setCorrelationId(getCorrelationId(request));
        error.setTimestamp(Instant.now());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
package com.agkminds.zenith.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e, WebRequest req) {
        return getErrorResponseEntity(e, req, HttpStatus.BAD_REQUEST);
    }

    // 401 Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e, WebRequest req) {
        return getErrorResponseEntity(e, req, HttpStatus.UNAUTHORIZED);
    }

    // 404 Not Found
    @ExceptionHandler({
            ResourceNotFoundException.class,
            CommentException.class,
            ChatException.class,
            MessageException.class,
            ReelException.class,
            StoryException.class,
            PostException.class,
            UserException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(Exception e, WebRequest req) {
        return getErrorResponseEntity(e, req, HttpStatus.NOT_FOUND);
    }

    // 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest req) {
        return getErrorResponseEntity(e, req, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> getErrorResponseEntity(Exception e, WebRequest req, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setError(req.getDescription(false));
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, status);
    }
}
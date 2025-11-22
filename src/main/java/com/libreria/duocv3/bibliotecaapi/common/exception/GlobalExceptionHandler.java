package com.libreria.duocv3.bibliotecaapi.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError onValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.FieldErrorItem> items = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ApiError.FieldErrorItem(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue()))
                .toList();
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Error de validación",
                req.getRequestURI(),
                items
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError onConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        List<ApiError.FieldErrorItem> items = ex.getConstraintViolations().stream()
                .map(cv -> new ApiError.FieldErrorItem(cv.getPropertyPath().toString(), cv.getMessage(), cv.getInvalidValue()))
                .toList();
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Parámetros inválidos",
                req.getRequestURI(),
                items
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError onDenied(AccessDeniedException ex, HttpServletRequest req) {
        return new ApiError(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Acceso denegado",
                req.getRequestURI(),
                null
        );
    }

    @ExceptionHandler({ ResponseStatusException.class })
    public ApiError onResponseStatus(ResponseStatusException ex, HttpServletRequest req) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        return new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getReason(),
                req.getRequestURI(),
                null
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError onUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "JSON mal formado o tipo de dato inválido",
                req.getRequestURI(),
                null
        );
    }

    // Para ErrorResponseException (Spring 6) como 404, 400, etc.
    @ExceptionHandler(ErrorResponseException.class)
    public ApiError onErrorResponse(ErrorResponseException ex, HttpServletRequest req) {
        ProblemDetail pd = ex.getBody();
        HttpStatus status = HttpStatus.valueOf(pd.getStatus());
        return new ApiError(
                status.value(),
                status.getReasonPhrase(),
                pd.getDetail(),
                req.getRequestURI(),
                null
        );
    }

    // Fallback 500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError onUnhandled(Exception ex, HttpServletRequest req) {
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Error interno del servidor",
                req.getRequestURI(),
                null
        );
    }
}

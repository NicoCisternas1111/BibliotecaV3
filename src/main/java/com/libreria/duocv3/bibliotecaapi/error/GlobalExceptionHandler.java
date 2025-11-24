package com.libreria.duocv3.bibliotecaapi.error;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.libreria.duocv3.bibliotecaapi.common.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // Errores internos de Swagger / OpenAPI
        @ExceptionHandler({
                        org.springdoc.api.OpenApiResourceNotFoundException.class,
                        org.springframework.web.HttpMediaTypeNotAcceptableException.class
        })
        public ResponseEntity<?> handleSwaggerInternalErrors(Exception ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // JSON mal formado
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ErrorResponse(
                                                "JSON inválido",
                                                "El body enviado no tiene el formato correcto"));
        }

        // Errores de validación (@Valid)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

                List<String> details = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                .collect(Collectors.toList());

                return ResponseEntity.badRequest()
                                .body(new ErrorResponse(
                                                "Error de validación",
                                                String.join(", ", details)));
        }

        // IllegalArgumentException (uso interno)
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
                return ResponseEntity.badRequest()
                                .body(new ErrorResponse("Solicitud inválida", ex.getMessage()));
        }

        // 🔹 ResponseStatusException → respetar el código (400, 404, etc.)
        @ExceptionHandler(ResponseStatusException.class)
        public ResponseEntity<ErrorResponse> handleResponseStatus(ResponseStatusException ex) {
                HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

                String title = (ex.getReason() != null && !ex.getReason().isBlank())
                                ? ex.getReason()
                                : "Error";

                String detail = (ex.getMessage() != null) ? ex.getMessage() : "";

                return ResponseEntity.status(status)
                                .body(new ErrorResponse(title, detail));
        }

        // Cualquier otra cosa realmente inesperada
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericErrors(Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponse(
                                                "Ha ocurrido un error inesperado",
                                                "Si el problema persiste, contacte al administrador."));
        }
}

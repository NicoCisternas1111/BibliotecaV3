package com.libreria.duocv3.bibliotecaapi.common.exception;

import java.time.Instant;
import java.util.List;

public class ApiError {
    public Instant timestamp = Instant.now();
    public int status;
    public String error;       // e.g. "Bad Request"
    public String message;     // mensaje principal
    public String path;        // request URI
    public List<FieldErrorItem> errors; // detalles de campos (opcional)

    public ApiError(int status, String error, String message, String path, List<FieldErrorItem> errors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    public static class FieldErrorItem {
        public String field;
        public String message;
        public Object rejectedValue;

        public FieldErrorItem(String field, String message, Object rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }
    }
}

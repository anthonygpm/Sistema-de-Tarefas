package dev.anthony.tarefas.exception;

import dev.anthony.tarefas.custom_messages.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(new ErrorMessage(ex.getMessage(), "NOT_FOUND"));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .status(400)
                .body(new ErrorMessage(ex.getMessage(), "BAD_REQUEST"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralError(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(new ErrorMessage("Erro interno no servidor.", "INTERNAL_SERVER_ERROR"));
    }
}

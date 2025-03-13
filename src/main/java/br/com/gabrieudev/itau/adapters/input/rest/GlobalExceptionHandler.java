package br.com.gabrieudev.itau.adapters.input.rest;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gabrieudev.itau.application.exceptions.InternalErrorException;
import br.com.gabrieudev.itau.application.exceptions.NotFoundException;
import br.com.gabrieudev.itau.application.exceptions.StandardException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardException> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StandardException standardException = new StandardException(HttpStatus.UNPROCESSABLE_ENTITY.value(), Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<StandardException> handleInternalErrorException(InternalErrorException e) {
        StandardException standardException = new StandardException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardException> handleNotFoundException(NotFoundException e) {
        StandardException standardException = new StandardException(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(standardException, HttpStatus.NOT_FOUND);
    }
}

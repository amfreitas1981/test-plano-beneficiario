package com.plano.saude.cadastro.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TreatErrors {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity treatError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity treatError400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity treatError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity treatErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credencials");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity treatErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failure");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity treatErrorAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity treatError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " +ex.getLocalizedMessage());
    }

    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}

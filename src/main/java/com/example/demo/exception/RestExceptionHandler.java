package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice // cette annotation utilisée pour déclarer que cette classe "RestExceptionHandler" comme un conseiller global pour les contrôleurs REST de l'application (afin de gérer les erreurs à tous les niveaux de l'application).
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // NB: Cette méthode ici est responsable de la gestion des exceptions de type "EntityNotFoundException" qui peut être levées dans n'importe quel contrôleur REST de l'application
    @ExceptionHandler(EntityNotFoundException.class) // cette annotation signifie que cette méthode ci-dessous gère les exceptions de type "EntityNotFoundException"
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exception, WebRequest webRequest) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorResponse errorResponse = ErrorResponse.builder() // construire un objet "ErrorResponse" contenant les détails de l'erreur, puis l'envoyer dans la reponse API.
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidEntityException exception, WebRequest webRequest) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(exception.getErrors())
                .build();

        return new ResponseEntity<>(errorResponse, badRequest);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidOperationException exception, WebRequest webRequest) {
        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, notFound);
    }
}

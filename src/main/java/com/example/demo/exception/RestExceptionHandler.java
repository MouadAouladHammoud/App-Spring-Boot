package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice // cette annotation utilisée pour déclarer que cette classe "RestExceptionHandler" comme un conseiller global pour les contrôleurs REST de l'application (afin de gérer les erreurs à tous les niveaux de l'application).
public class RestExceptionHandler {

    // EntityNotFoundException est une exception personnalisée utilisée pour gérer les erreurs lorsqu'une entité n'est pas trouvée dans la base de données.
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

    // Gestion des erreurs global de validation
    // NB: L'exception de type "MethodArgumentNotValidException" est une exception globale qui est levée lorsqu'une requête entrante ne respecte pas les contraintes de validation des arguments, et que cette exception n'est pas capturée par des gestionnaires d'exceptions personnalisés (Les exceptions personnalisées définies ci-dessus : InvalidOperationException, InvalidEntityException et EntityNotFoundException).
    // Lorsque utiliser l'annotation @ExceptionHandler pour gérer globalement les exceptions telles que "MethodArgumentNotValidException", il n'est pas nécessaire d'étendre la classe "ResponseEntityExceptionHandler".
    //   En fait, ne pas étendre cette classe peut éviter des conflits et des ambiguïtés dans la gestion des exceptions. car la classe "ResponseEntityExceptionHandler" fournit déjà une implémentation par défaut pour plusieurs exceptions, y compris "MethodArgumentNotValidException"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        // Extraction des messages d'erreur de validation
        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCodes.VALIDATION_ERROR)
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, badRequest);
    }
}

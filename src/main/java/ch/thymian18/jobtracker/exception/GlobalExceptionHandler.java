package ch.thymian18.jobtracker.exception;

import ch.thymian18.jobtracker.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
@RestControllerAdvice markiert eine Klasse als globalen Aspekt,
der sich quer über alle (oder ausgewählte) Controller in deiner Anwendung legt —
speziell für die Behandlung von Exceptions.
Stell es dir vor wie ein Sicherheitsnetz, das unter all deinen Controllern aufgespannt ist:
Egal, in welchem Controller (oder im Service, der vom Controller aufgerufen wird) eine Exception auftaucht,
landet sie zuerst in diesem Netz, bevor Spring sie unbehandelt nach aussen durchlassen würde.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}

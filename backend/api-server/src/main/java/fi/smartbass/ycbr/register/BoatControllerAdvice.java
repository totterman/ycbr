package fi.smartbass.ycbr.register;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice("org.example.controllers")
public class BoatControllerAdvice {

    @ExceptionHandler(BoatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String boatNotFoundHandler(BoatNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BoatAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String boatAlreadyExistsHandler(BoatAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BoatRequestMalformedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String boatRequestMalformedHandler(BoatRequestMalformedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

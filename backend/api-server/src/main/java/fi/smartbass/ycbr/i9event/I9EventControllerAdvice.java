package fi.smartbass.ycbr.i9event;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class I9EventControllerAdvice {

    @ExceptionHandler(I9EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String boatNotFoundHandler(I9EventNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(I9EventAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String boatAlreadyExistsHandler(I9EventAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(I9EventRequestMalformedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String boatRequestMalformedHandler(I9EventRequestMalformedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BookingExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String bookingExistsHandler(BookingExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InspectorExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String inspectorExistsHandler(InspectorExistsException ex) {
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

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String npeHandler(NullPointerException npe) { return  "Unknown attributes in payload."; }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String dateTimeHandler(DateTimeParseException dpe) { return  "Unprocessable datetime values: " + dpe.getMessage(); }

}

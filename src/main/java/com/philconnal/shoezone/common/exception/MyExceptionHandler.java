package com.philconnal.shoezone.common.exception;

import com.philconnal.shoezone.common.exception.errors.MyBadRequestException;
import com.philconnal.shoezone.common.exception.errors.MyExistedException;
import com.philconnal.shoezone.common.exception.errors.MyParseDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyExistedException.class)
    public ResponseEntity<ApiError> existed(MyExistedException exception) {
        HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;
        final ApiError apiError = getApiError(exception, methodNotAllowed);
        return new ResponseEntity<>(apiError, methodNotAllowed);
    }

    @ExceptionHandler(MyBadRequestException.class)
    public ResponseEntity<ApiError> existed(MyBadRequestException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ApiError apiError = getApiError(exception, badRequest);
        return new ResponseEntity<>(apiError, badRequest);
    }

    @ExceptionHandler(MyParseDateException.class)
    public ResponseEntity<ApiError> invalidToken(MyParseDateException exception) {
        HttpStatus forbidden = HttpStatus.BAD_REQUEST;
        final ApiError apiError = getApiError(exception, forbidden);
        return new ResponseEntity<>(apiError, forbidden);
    }

    private ApiError getApiError(Exception e, HttpStatus status) {
        ApiError apiError = new ApiError();
        apiError.setError(status.value());
        apiError.setStatus(status);
        apiError.setMessage(e.getMessage());
        apiError.setDescription("");
        apiError.setTimestamp(ZonedDateTime.now(ZoneId.of("Z")));
        return apiError;
    }


}

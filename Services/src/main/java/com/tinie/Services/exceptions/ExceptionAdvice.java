package com.tinie.Services.exceptions;

import com.tinie.Services.responses.UnauthorisedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * Handle thrown {@link UnauthorisedException} and return a {@link ResponseEntity} with status code of {@literal 401}
     * @param e Instance of {@link UnauthorisedException} thrown
     * @return Instance of {@link ResponseEntity}
     */
    @ExceptionHandler(value = UnauthorisedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<UnauthorisedResponse> unauthorisedExceptionHandler(UnauthorisedException e){
        var unauthorisedResponse = new UnauthorisedResponse();
        unauthorisedResponse.setStatus(HttpStatus.UNAUTHORIZED.toString());
        unauthorisedResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        unauthorisedResponse.setTimestamp(new Date().getTime());
        unauthorisedResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(unauthorisedResponse, HttpStatus.UNAUTHORIZED);
    }
}

package org.example.afarm.Exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionH {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> RunException(RuntimeException e){
        System.out.println(e);
        return new ResponseEntity<>(e, HttpStatusCode.valueOf(403));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> NullHendler(NullPointerException ne){
        return new ResponseEntity<>(ne.getMessage(),HttpStatusCode.valueOf(406));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> IoHendler(IOException ne){
        System.out.println(ne);
        return new ResponseEntity<>(ne.getMessage(),HttpStatusCode.valueOf(409));
    }
}

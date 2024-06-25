package org.example.afarm.Exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;

@RestControllerAdvice
public class ExceptionH {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> RunException(RuntimeException e){
        System.out.println(e);
        return new ResponseEntity<>(e, HttpStatusCode.valueOf(500));
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

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> NotFound(ChangeSetPersister.NotFoundException no){
        System.out.println(no);
        return new ResponseEntity<>("찾을 수 없는 주소입니다.",HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(KeyAlreadyExistsException.class)
    public ResponseEntity<?> AlreadyExist(KeyAlreadyExistsException key){
        return new ResponseEntity<>("이미 해당값이 존재합니다.",HttpStatusCode.valueOf(400));
    }
}

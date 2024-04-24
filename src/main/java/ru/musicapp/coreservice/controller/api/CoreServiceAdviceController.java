package ru.musicapp.coreservice.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.musicapp.coreservice.model.dto.error.ErrorDto;

@ControllerAdvice
public class CoreServiceAdviceController {


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.internalServerError().body(ErrorDto.builder()
                .message(ex.getMessage())
                .build());
    }

}

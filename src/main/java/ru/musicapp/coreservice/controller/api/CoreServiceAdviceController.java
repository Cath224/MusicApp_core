package ru.musicapp.coreservice.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.musicapp.coreservice.model.dto.error.ErrorDto;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class CoreServiceAdviceController {


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex) {
        log.error("500 Error with ex = {}", ex.getMessage());
        log.error("500 Error with stack trace = {}", Arrays.toString(ex.getStackTrace()));
        return ResponseEntity.internalServerError().body(ErrorDto.builder()
                .message(ex.getMessage())
                .build());
    }

}

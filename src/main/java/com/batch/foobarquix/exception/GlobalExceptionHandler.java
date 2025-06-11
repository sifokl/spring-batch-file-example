package com.batch.foobarquix.exception;

import com.batch.foobarquix.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JobExecutionException.class)
    public ResponseEntity<ApiResponse<String>> handleJobExecution(JobExecutionException ex) {
        log.error("Batch execution failed: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(new ApiResponse<>(false, ex.getMessage(), "Échec de l'exécution du batch."));

    }


    @ExceptionHandler(InvalidNumberRangeException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidNumberRangeException(InvalidNumberRangeException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));

    }

    @ExceptionHandler(MissingBatchInputFileException.class)
    public ResponseEntity<String> handleMissingBatchInputFile(MissingBatchInputFileException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ApiResponse response = new ApiResponse<>(
                false,
                HttpStatus.BAD_REQUEST.value()+" : "+
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Format de paramètre invalide : " + ex.getValue()
        );
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllOtherErrors(Exception ex) {
        return ResponseEntity.internalServerError().body(new ApiResponse<>(false, "Erreur interne : " + ex.getMessage(), null));
    }

}

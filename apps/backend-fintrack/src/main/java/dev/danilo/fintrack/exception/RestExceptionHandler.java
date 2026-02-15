package dev.danilo.fintrack.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(FintrackException.class)
  public ResponseEntity<ProblemDetail> handleFintrackException(FintrackException exc) {
    return ResponseEntity.status(exc.toProblemDetail().getStatus()).body(exc.toProblemDetail());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {

    List<InvalidParam> fieldErrors =
        exc.getFieldErrors().stream()
            .map(f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
            .toList();

    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problemDetail.setTitle("Your request parameters didn't validate.");
    problemDetail.setProperty("invalid-params", fieldErrors);

    return problemDetail;
  }

  private record InvalidParam(String name, String reason) {}
}

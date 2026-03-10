package dev.danilo.fintrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class UserEmailAlreadyExistsException extends FintrackException {

  private String email;

  public UserEmailAlreadyExistsException(String email) {
    this.email = email;
  }

  @Override
  public ProblemDetail toProblemDetail() {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
    problemDetail.setTitle("Email already in use");
    problemDetail.setDetail(String.format("The email address '%s' is already registered.", email));

    return problemDetail;
  }
}

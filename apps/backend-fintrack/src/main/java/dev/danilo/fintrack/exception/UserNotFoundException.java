package dev.danilo.fintrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;



public class UserNotFoundException extends FintrackException {
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("User Not Found");
        problemDetail.setDetail("The user with the given augment does not exist.");

        return problemDetail;
    }
}

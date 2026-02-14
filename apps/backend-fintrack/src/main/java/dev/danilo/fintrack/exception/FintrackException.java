package dev.danilo.fintrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class FintrackException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Fintrack Internal Server Error");
        problemDetail.setDetail("An unexpected error occurred. Please try again later.");

        return problemDetail;
    }
}

package dev.danilo.fintrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AuthenticationRequiredException extends FintrackException {
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

        problemDetail.setTitle("Authentication Required");
        problemDetail.setDetail("Authentication is required to access this resource.");

        return problemDetail;
    }
}

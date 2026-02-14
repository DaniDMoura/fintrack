package dev.danilo.fintrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidMonthFormatException extends FintrackException{

    private final String month;

    public InvalidMonthFormatException(String month) {
        this.month = month;
    }

    @Override
    public ProblemDetail toProblemDetail() {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Invalid Month Format");

        problemDetail.setDetail(
                "Month must follow pattern yyyy-MM. Received: " + month
        );

        return problemDetail;
    }
}

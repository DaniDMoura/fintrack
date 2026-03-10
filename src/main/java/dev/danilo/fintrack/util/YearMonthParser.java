package dev.danilo.fintrack.util;

import dev.danilo.fintrack.exception.InvalidMonthFormatException;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import org.springframework.stereotype.Component;

@Component
public class YearMonthParser {
  public YearMonth parseMonthOrNow(String month) {
    if (month == null || month.isBlank()) {
      return YearMonth.now();
    }

    try {
      return YearMonth.parse(month);
    } catch (DateTimeParseException ex) {
      throw new InvalidMonthFormatException(month);
    }
  }
}

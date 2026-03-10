package dev.danilo.fintrack.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;
import java.time.YearMonth;

@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, LocalDate> {

  @Override
  public LocalDate convertToDatabaseColumn(YearMonth yearMonth) {
    return yearMonth == null ? null : yearMonth.atDay(1);
  }

  @Override
  public YearMonth convertToEntityAttribute(LocalDate localDate) {
    return localDate == null ? null : YearMonth.from(localDate);
  }
}

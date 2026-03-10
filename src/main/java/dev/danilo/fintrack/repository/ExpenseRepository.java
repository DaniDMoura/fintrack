package dev.danilo.fintrack.repository;

import dev.danilo.fintrack.dto.response.ExpenseSummaryResponse;
import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  List<Expense> findByUserAndCreatedAtBetweenOrderByCreatedAtDesc(
      User user, LocalDateTime start, LocalDateTime end);

  List<Expense> findByUserOrderByCreatedAtDesc(User user);

  Optional<Expense> findByIdAndUser(User user, Long id);

  @Query("""
    SELECT COALESCE(SUM(e.amount),0)
    FROM Expense e
    WHERE e.user = :user
      AND e.yearMonth = :yearMonth
""")
  BigDecimal sumTotalExpenseByUserAndYearMonth(
          @Param("user") User user,
          @Param("yearMonth") YearMonth yearMonth);

  @Query(
      """
    SELECT e
    FROM Expense e
    WHERE (:userId IS NULL OR e.user.id = :userId)
      AND (:yearMonth IS NULL OR e.yearMonth = :yearMonth)
    ORDER BY e.createdAt DESC
""")
  List<Expense> findWithFilters(
      @Param("userId") Long userId, @Param("yearMonth") YearMonth yearMonth);

  List<Expense> findByUserAndYearMonthOrderByCreatedAtDesc(User user, YearMonth yearMonth);

  @Query(
      """
    SELECT e.yearMonth, COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.user = :user
      AND e.yearMonth BETWEEN :start AND :end
    GROUP BY e.yearMonth
    ORDER BY e.yearMonth
""")
  List<ExpenseSummaryResponse> sumExpensesByUserAndYear(User user, YearMonth start, YearMonth end);
}

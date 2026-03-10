package dev.danilo.fintrack.repository;

import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncomeRepository extends JpaRepository<Income, Long> {
  @Query(
      """
            SELECT i
            FROM Income i
            WHERE (:userId IS NULL OR i.user.id = :userId)
              AND (:yearMonth IS NULL OR i.yearMonth = :yearMonth)
            ORDER BY i.yearMonth DESC
        """)
  List<Income> findWithFilters(
      @Param("userId") Long userId, @Param("yearMonth") YearMonth yearMonth);

  Optional<Income> findFirstByUserOrderByYearMonthDescCreatedAtDesc(User user);

  Optional<Income> findFirstByUserAndYearMonthLessThanEqualOrderByYearMonthDescCreatedAtDesc(
      User user, YearMonth yearMonth);

  @Query(
      """
    SELECT i
    FROM Income i
    WHERE i.user = :user
      AND i.yearMonth BETWEEN :start AND :end
    ORDER BY i.yearMonth ASC, i.createdAt ASC
""")
  List<Income> findAllByUserAndYear(User user, YearMonth start, YearMonth end);
}

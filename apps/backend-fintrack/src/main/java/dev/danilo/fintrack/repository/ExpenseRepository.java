package dev.danilo.fintrack.repository;

import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserAndCreatedAtBetweenOrderByCreatedAtDesc(User user, LocalDateTime start, LocalDateTime end);
    List<Expense> findByUserOrderByCreatedAtDesc(User user);
    Optional<Expense> findByIdAndUser(User user, Long id);

    @Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.user = :user
      AND e.createdAt >= :start
      AND e.createdAt < :end
""")
    BigDecimal sumByUserAndCreatedAtBetween(
            @Param("user") User user,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT e
        FROM Expense e
        WHERE (:userId IS NULL OR e.user.id = :userId)
          AND (:start IS NULL OR e.createdAt >= :start)
          AND (:end IS NULL OR e.createdAt < :end)
        ORDER BY e.createdAt DESC
    """)
    List<Expense> findWithFilters(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}

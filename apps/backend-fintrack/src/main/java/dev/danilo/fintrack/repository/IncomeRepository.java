package dev.danilo.fintrack.repository;

import dev.danilo.fintrack.entity.Expense;
import dev.danilo.fintrack.entity.Income;
import dev.danilo.fintrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Optional<Income> findByUser(User user);

    @Query("""
        SELECT i
        FROM Income i
        WHERE (:userId IS NULL OR i.user.id = :userId)
          AND (:start IS NULL OR i.createdAt >= :start)
          AND (:end IS NULL OR i.createdAt < :end)
        ORDER BY i.createdAt DESC
    """)
    List<Income> findWithFilters(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}

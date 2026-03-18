package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.RewardDiscipline;
import com.kenji.qlnv_backend.enums.RewardDisciplineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RewardDisciplineRepository extends JpaRepository<RewardDiscipline, Long> {
    List<RewardDiscipline> findAllByDecisionDate(LocalDate decisionDate);

    List<RewardDiscipline> findAllByEmployee(Employee employee);

    @Query("""
        SELECT rd
        FROM RewardDiscipline rd
        WHERE rd.employee = :employee
          AND rd.type = :type
          AND rd.decisionDate BETWEEN :startDate AND :endDate
        ORDER BY rd.decisionDate
    """)
    List<RewardDiscipline> findByEmployeeAndTypeAndMonth(
            @Param("employee") Employee employee,
            @Param("type") RewardDisciplineType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

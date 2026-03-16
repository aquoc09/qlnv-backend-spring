package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import com.kenji.qlnv_backend.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Long> {
    @Query("""
        SELECT lr
        FROM LeaveRecord lr
        WHERE lr.employee = :employee
          AND lr.status = :status
          AND lr.startDate <= :endDate
          AND lr.endDate >= :startDate
        ORDER BY lr.startDate
    """)
    List<LeaveRecord> findAcceptedLeaveInMonth(
            @Param("employee") Employee employee,
            @Param("status") LeaveStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

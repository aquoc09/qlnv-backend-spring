package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    Optional<LeaveBalance> findByEmployee(Employee employee);

    Optional<LeaveBalance> findByEmployeeAndYear(Employee employee, int year);

    boolean existsByEmployeeAndYear(Employee employee, int year);
}

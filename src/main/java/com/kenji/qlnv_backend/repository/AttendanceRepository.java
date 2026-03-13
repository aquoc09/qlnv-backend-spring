package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Attendance;
import com.kenji.qlnv_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByEmployee(Employee employee);

    Optional<Attendance> findByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

    List<Attendance> findAllByWorkDate(LocalDate workDate);
}

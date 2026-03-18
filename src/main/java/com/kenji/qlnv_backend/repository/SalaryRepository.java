package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findAllByMonthAndYear(Integer month, Integer year);
    List<Salary> findAllByEmployee(Employee employee);
}
package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Contract;
import com.kenji.qlnv_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByEmployee(Employee employee);
}

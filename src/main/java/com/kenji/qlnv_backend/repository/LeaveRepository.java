package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.Leave;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import com.kenji.qlnv_backend.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
}

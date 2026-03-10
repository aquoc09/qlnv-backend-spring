package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.LeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Long> {
}

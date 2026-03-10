package com.kenji.qlnv_backend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveRecordResponse {
    Long id;
    EmployeeResponse employee;
    LeaveResponse leave;
    LocalDate startDate;
    LocalDate endDate;
    String reason;
    String status;
}

package com.kenji.qlnv_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveRecordRequest {
    Long employeeId;
    Long leaveId;
    LocalDate startDate;
    LocalDate endDate;
    String reason;
    String status;
}

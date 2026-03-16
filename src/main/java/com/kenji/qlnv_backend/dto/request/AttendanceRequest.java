package com.kenji.qlnv_backend.dto.request;

import com.kenji.qlnv_backend.enums.AttendanceStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest {
    Long employeeId;
    LocalDate workDate;
    LocalTime checkIn;
    LocalTime checkOut;
    AttendanceStatus status;
}

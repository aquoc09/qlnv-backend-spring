package com.kenji.qlnv_backend.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveBalanceRequest {
    @NonNull
    Long employeeId;
    @Min(2020)
    Integer year;
    @Min(12)
    Integer totalDays;
    @Min(0)
    Integer usedDays;
}

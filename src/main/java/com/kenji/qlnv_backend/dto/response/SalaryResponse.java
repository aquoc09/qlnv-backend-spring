package com.kenji.qlnv_backend.dto.response;

import com.kenji.qlnv_backend.enums.SalaryStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryResponse {
    Long id;
    EmployeeResponse employee;
    Integer month;
    Integer year;
    BigDecimal baseSalary;
    BigDecimal allowance;
    BigDecimal bonus;
    BigDecimal deduction;
    BigDecimal netSalary;
    LocalDate paymentDate;
    SalaryStatus status;
}

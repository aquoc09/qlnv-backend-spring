package com.kenji.qlnv_backend.dto.request;

import com.kenji.qlnv_backend.enums.Gender;
import com.kenji.qlnv_backend.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {

    String fullName;
    Gender gender;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    @Email(message = "INVALID_MAIL")
    String email;
    String phone;
    String address;
    LocalDate hireDate;
    String position;
    String payCode;

    Long departmentId;

    String username;
}

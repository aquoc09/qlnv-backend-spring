package com.kenji.qlnv_backend.dto.response;

import com.kenji.qlnv_backend.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    String id;

    String fullName;
    Gender gender;
    Date dob;
    String email;
    String phone;
    String address;
    Date hireDate;
    String position;

    DepartmentResponse department;

    UserResponse user;
}

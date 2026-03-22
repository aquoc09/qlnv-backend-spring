package com.kenji.qlnv_backend.dto.response;

import com.kenji.qlnv_backend.entity.Employee;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    String id;

    String name;

    Long managerId;
}

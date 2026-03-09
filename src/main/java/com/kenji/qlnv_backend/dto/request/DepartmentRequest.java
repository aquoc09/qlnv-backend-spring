package com.kenji.qlnv_backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequest {
    String name;
    Long managerId;
    List<Long> employees;
}

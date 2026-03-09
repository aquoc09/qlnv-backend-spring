package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.DepartmentRequest;
import com.kenji.qlnv_backend.dto.response.DepartmentResponse;
import com.kenji.qlnv_backend.entity.Department;
import com.kenji.qlnv_backend.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Department toDepartment(DepartmentRequest request);

    DepartmentResponse toDepartmentResponse(Department dep);

    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "employees", ignore = true)
    void updateDepartment(@MappingTarget Department dep, DepartmentRequest request);

//    // mapping employees
//    default List<String> getEmployeeIds(List<Employee> employees) {
//        if (employees == null) return List.of();
//        return employees.stream().map(Employee::getEmpId).toList();
//    }
}

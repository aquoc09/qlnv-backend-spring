package com.kenji.qlnv_backend.repository;

import com.kenji.qlnv_backend.entity.Department;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser_Username(String username);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUser(User user);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.fullName) LIKE CONCAT('%', LOWER(?1), '%')")
    List<Employee> findByEmpName(String name);

    List<Employee> findByDepartment(Department department);

    @Modifying
    @Query("DELETE FROM Employee e WHERE e.id = :id")
    void deleteByIdHard(@Param("id") Long id);
}

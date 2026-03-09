package com.kenji.qlnv_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    Long id;

    @JoinColumn(name = "department_name", unique = true)
    String name;

    //OneToOne to manager
    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
    Employee manager;

    //OneToMany to list employees of department
    @OneToMany(mappedBy = "department")
    List<Employee> employees;

}

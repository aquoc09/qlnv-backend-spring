package com.kenji.qlnv_backend.entity;

import com.kenji.qlnv_backend.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    Long id;


    String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "dob", length = 10)
    LocalDate dob;

    @Column (name = "email", unique = true)
    String email;

    @Column (name = "phone", unique = true)
    String phone;

    @Column (name = "address")
    String address;

    @Column(name = "hire_date", length = 10)
    LocalDate hireDate;

    @Column(name = "position")
    String position;

    @Column(name = "pay_code")
    String payCode;

    //OneToOne to Department
    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department;

    //OneToOne to User
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    User user;
}

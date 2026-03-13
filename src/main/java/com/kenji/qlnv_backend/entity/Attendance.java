package com.kenji.qlnv_backend.entity;

import com.kenji.qlnv_backend.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    Employee employee;

    @Column(name = "work_date")
    LocalDate workDate;

    @Column(name = "check_in")
    LocalTime checkIn;

    @Column(name = "check_out")
    LocalTime checkOut;

    @Enumerated(EnumType.STRING)
    AttendanceStatus status;

    @Column(name = "is_check_out")
    boolean isCheckOut;

    @Column(name = "time_worked")
    int timeWorked;
}

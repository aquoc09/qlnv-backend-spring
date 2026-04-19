package com.kenji.qlnv_backend.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(
        name = "leave_balance",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "year"})
        }
)
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    int year;

    @Builder.Default
    @Column(name = "total_days")
    int totalDays = 12;

    @Column(name = "used_days")
    int usedDays;
}

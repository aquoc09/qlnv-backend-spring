package com.kenji.qlnv_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "jwt_id", unique = true)
    String jwtId;

    @Column(name = "refresh_token", unique = true)
    String refreshToken;

    @Column(name = "expiration_time")
    LocalDateTime expirationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}

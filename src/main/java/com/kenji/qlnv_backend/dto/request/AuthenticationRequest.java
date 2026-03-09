package com.kenji.qlnv_backend.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    @Size(min = 5, message = "INVALID_USERNAME")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

}

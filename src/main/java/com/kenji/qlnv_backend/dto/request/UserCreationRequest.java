package com.kenji.qlnv_backend.dto.request;

import com.kenji.qlnv_backend.entity.Role;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 5, message = "INVALID_USERNAME")
    String username;
    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

//    //OneToMany to Role
//    @OneToMany
    Set<Role> roles;
}

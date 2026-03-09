package com.kenji.qlnv_backend.configuration;

import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.enums.RoleEnum;
import com.kenji.qlnv_backend.repository.RoleRepository;
import com.kenji.qlnv_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Bean
    @Order(1)
    ApplicationRunner initDefaultRole(RoleRepository roleRepository){
        return args -> {
            for(RoleEnum roleEnum : RoleEnum.values()){
                if(!roleRepository.existsById(roleEnum.name())){
                    roleRepository.save(
                            Role.builder()
                                    .name(roleEnum.name())
                                    .build()
                    );
                    log.info("Role {} has been created", roleEnum.name());
                }
            }
        };
    }

    @Bean
    @Order(2)
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
          if(userRepository.findByUsername("admin").isEmpty()){
              Set<Role> roles = new HashSet<>();
              roles.add(Role.builder()
                              .name(RoleEnum.ADMIN.name())
                      .build());

              var user = User.builder()
                      .username("admin")
                      .password(passwordEncoder.encode("admin"))
                      .roles(roles)
                      .build();

              userRepository.save(user);
              log.info("Admin user has been created with default username and password");
          }
        };
    }


}

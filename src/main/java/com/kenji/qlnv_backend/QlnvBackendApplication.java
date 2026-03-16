package com.kenji.qlnv_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QlnvBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(QlnvBackendApplication.class, args);
	}

}

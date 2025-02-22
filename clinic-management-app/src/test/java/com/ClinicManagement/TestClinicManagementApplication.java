package com.ClinicManagement;

import org.springframework.boot.SpringApplication;

public class TestClinicManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(ClinicManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

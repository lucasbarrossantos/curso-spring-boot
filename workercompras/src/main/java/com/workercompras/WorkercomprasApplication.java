package com.workercompras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WorkercomprasApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkercomprasApplication.class, args);
	}

}

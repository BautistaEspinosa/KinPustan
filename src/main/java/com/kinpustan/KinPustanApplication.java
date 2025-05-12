package com.kinpustan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.kinpustan.repository")
@EntityScan(basePackages = "com.kinpustan.model")
@SpringBootApplication
public class KinPustanApplication {
	private static final Logger logger = LoggerFactory.getLogger(KinPustanApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciando aplicación KinPustan...");
		SpringApplication.run(KinPustanApplication.class, args);
		logger.info("Aplicación KinPustan iniciada correctamente.");
	}

}
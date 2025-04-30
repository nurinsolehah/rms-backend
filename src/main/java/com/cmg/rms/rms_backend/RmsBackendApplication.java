package com.cmg.rms.rms_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "my.com.rms.rms_backend.model")
@EnableJpaRepositories(basePackages = "com.cmg.rms.rms_backend.repository.jpa")
public class RmsBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(RmsBackendApplication.class, args);
  }
}

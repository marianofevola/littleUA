package com.acme.littleUA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.acme.littleUA.entity")
@EnableJpaRepositories(basePackages = {"com.acme.littleUA.repository"})
@SpringBootApplication
public class LittleUaApplication {
  public static void main(String[] args) {
    SpringApplication.run(LittleUaApplication.class, args);
  }

}

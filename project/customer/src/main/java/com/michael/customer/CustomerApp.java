package com.michael.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.michael.clients")
public class CustomerApp {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApp.class, args);
    }
}

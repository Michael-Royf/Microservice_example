package com.michael.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("fraud")
public interface FraudClients {
    @GetMapping(path = "/api/v1/fraud-check/{customerId}")
    FraudCheckResponse fraudCheckResponseIsFraudster(@PathVariable("customerId") Long customerId);

}


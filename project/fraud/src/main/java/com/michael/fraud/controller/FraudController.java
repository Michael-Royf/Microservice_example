package com.michael.fraud.controller;


import com.michael.clients.FraudCheckResponse;
import com.michael.fraud.service.FraudCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fraud-check")
public class FraudController {

    private final FraudCheckService fraudCheckService;

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public FraudCheckResponse fraudCheckResponseIsFraudster(@PathVariable("customerId") Long customerId) {
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        log.info("fraud check request for customer {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }

}

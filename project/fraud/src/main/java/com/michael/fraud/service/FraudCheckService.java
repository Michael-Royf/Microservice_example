package com.michael.fraud.service;

public interface FraudCheckService {
    Boolean isFraudulentCustomer(Long customerId);
}

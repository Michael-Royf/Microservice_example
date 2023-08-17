package com.michael.fraud.service.impl;

import com.michael.fraud.entity.FraudCheckHistory;
import com.michael.fraud.repository.FraudCheckHistoryRepository;
import com.michael.fraud.service.FraudCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FraudCheckServiceImpl implements FraudCheckService {

    private final FraudCheckHistoryRepository fraudRepository;


    @Override
    public Boolean isFraudulentCustomer(Long customerId) {
        fraudRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        log.info("Fraud check request for customer {}", customerId);
        return false;
    }
}

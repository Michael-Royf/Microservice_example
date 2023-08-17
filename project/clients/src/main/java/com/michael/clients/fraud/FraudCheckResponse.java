package com.michael.clients.fraud;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FraudCheckResponse {
    private Boolean isFraudster;
}

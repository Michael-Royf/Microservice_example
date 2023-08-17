package com.michael.clients;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FraudCheckResponse {
    private Boolean isFraudster;
}

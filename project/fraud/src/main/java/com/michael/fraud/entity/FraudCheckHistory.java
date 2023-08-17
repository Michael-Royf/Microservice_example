package com.michael.fraud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class FraudCheckHistory {
    @Id
    @SequenceGenerator(
            name = "fraud_id_sequence",
            sequenceName = "fraud_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fraud_id_sequence")
    private Long id;
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "is_fraudster", nullable = false)
    private Boolean isFraudster;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdAt;
}

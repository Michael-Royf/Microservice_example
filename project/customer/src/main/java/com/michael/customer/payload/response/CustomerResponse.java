package com.michael.customer.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}

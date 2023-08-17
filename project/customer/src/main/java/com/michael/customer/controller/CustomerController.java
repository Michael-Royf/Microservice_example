package com.michael.customer.controller;

import com.michael.customer.payload.request.CustomerRequest;
import com.michael.customer.payload.response.CustomerResponse;
import com.michael.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        log.info("New customer registration {}", customerRequest);
        return new ResponseEntity<>(customerService.registerCustomer(customerRequest), CREATED);
    }


    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), OK);
    }


    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(customerService.deleteCustomer(customerId), OK);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long customerId,
                                                           @RequestBody @Valid CustomerRequest customerRequest) {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, customerRequest), OK);
    }
}

package com.michael.customer.service;

import com.michael.customer.payload.request.CustomerRequest;
import com.michael.customer.payload.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse registerCustomer(CustomerRequest customerRequest);
    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long customerId);

    CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest);

    String deleteCustomer(Long customerId);
}

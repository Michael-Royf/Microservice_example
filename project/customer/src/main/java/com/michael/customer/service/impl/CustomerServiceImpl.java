package com.michael.customer.service.impl;

import com.michael.clients.FraudCheckResponse;
import com.michael.clients.FraudClients;
import com.michael.customer.entity.Customer;
import com.michael.customer.payload.request.CustomerRequest;
import com.michael.customer.payload.response.CustomerResponse;
import com.michael.customer.repository.CustomerRepository;
import com.michael.customer.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final FraudClients fraudClients;
    //private final RestTemplate restTemplate;


    @Override
    public CustomerResponse registerCustomer(CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new RuntimeException(String.format("Customer with email %s already exists", customerRequest.getEmail()));
        }

        Customer customer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .build();

        customerRepository.saveAndFlush(customer);

//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId());


        FraudCheckResponse fraudCheckResponse = fraudClients.fraudCheckResponseIsFraudster(customer.getId());

        if (fraudCheckResponse.getIsFraudster()) {
            throw new IllegalStateException("Fraudster");
        }
        return mapper.map(customer, CustomerResponse.class);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> mapper.map(customer, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        return mapper.map(getCustomerFromDBById(customerId), CustomerResponse.class);
    }

    @Override
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = getCustomerFromDBById(customerId);
        checkNewEmail(customer, customerRequest);

        customer.setEmail(customerRequest.getEmail());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer = customerRepository.save(customer);
        return mapper.map(customer, CustomerResponse.class);
    }

    private void checkNewEmail(Customer currentCustomer, CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            if (!currentCustomer.getEmail().equals(customerRequest.getEmail())) {
                throw new RuntimeException(String.format("Customer with email %s already exists", customerRequest.getEmail()));
            }
        }
    }


    @Override
    public String deleteCustomer(Long customerId) {
        Customer customer = getCustomerFromDBById(customerId);
        customerRepository.delete(customer);
        return String.format("Customer with id %s was deleted", customerId);
    }

    private Customer getCustomerFromDBById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with id %s not found", id)));
    }
}

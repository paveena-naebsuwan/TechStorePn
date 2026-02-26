package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.CustomerNotFoundException;
import org.iths.techstore.Model.Customer;
import org.iths.techstore.Repository.CustomerRepository;
import org.iths.techstore.Validator.CustomerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        customerValidator.validate(customer);

        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        logger.info("Getting customer with id {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Get customer failed. Customer with id {} not found.", id);
                    return new CustomerNotFoundException
                            ("Customer with id: " + id + " does not exist.");
                });
    }

    public Customer updateCustomer(Long id, Customer customer) {
        if (!customerRepository.existsById(id)) {
            logger.warn("Update customer failed. Customer with id {} not found.", id);
            throw new CustomerNotFoundException
                    ("Customer with id: " + id + " does not exist.");
        }
        customerValidator.validate(customer);
        customer.setId(id);

        logger.info("Customer with id {} updated.", id);

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            logger.warn("Delete customer failed. Customer with id {} not found.", id);
            throw new CustomerNotFoundException
                    ("Customer with id: " + id + " does not exist.");
        }
        customerRepository.deleteById(id);
        logger.info("Customer with id {} deleted.", id);
    }
}

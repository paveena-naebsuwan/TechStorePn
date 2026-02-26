package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.CustomerNotValidateException;
import org.iths.techstore.Model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {
    private static final Logger logger =
            LoggerFactory.getLogger(CustomerValidator.class);

    public void validate(Customer customer) {
        validateCustomerName(customer.getName());
        validateCustomerTelephoneNumber(customer.getTelefonNumber());
        validateCustomerEmail(customer.getEmail());
    }

    public void validateCustomerName(String name) {
        if (name == null || name.isEmpty()) {
            logger.warn("Customer name is invalid");
            throw new CustomerNotValidateException("Customer name is invalid");
        }
    }

    public void validateCustomerTelephoneNumber(String telephoneNumber) {
        if (telephoneNumber == null || telephoneNumber.isEmpty()) {
            logger.warn("Customer telephone number is invalid");
            throw new CustomerNotValidateException("Customer telephone number is invalid.");
        }
    }

    public void validateCustomerEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.warn("Customer email is invalid");
            throw new CustomerNotValidateException("Email is invalid.");
        }
    }
}

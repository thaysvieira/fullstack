package com.spring.fullstack.customer;

import com.spring.fullstack.exception.DuplicateResourceException;
import com.spring.fullstack.exception.ResourceBadRequestException;
import com.spring.fullstack.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check if emails exists
        String email = customerRegistrationRequest.email();
        if(customerDao.existsCustomerWithEmail(email)){
            throw new DuplicateResourceException("email already taken");
        }
        //add
        Customer customer=new Customer(customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age());
        customerDao.insertCustomer(customer);


    }
    public void deleteCustomer(Integer id){
       Optional <Customer> customer = customerDao.selectCustomerById(id);
       if(customer.isEmpty()){
           throw  new ResourceNotFoundException(
                   "customer with id [%s] not found".formatted(id));
       }
       customerDao.deleteCustomerById(id);
    }
    public void updateCustomer(Integer customerId,
                               CustomerUpdateRequest updateRequest) {
        // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new ResourceBadRequestException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
    }


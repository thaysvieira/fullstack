package com.spring.fullstack.customer;

import com.spring.fullstack.exception.ResourceBadRequestException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    private static final List<Customer> customers;
    static {
        Customer alex = new Customer(1,
                "Alex",
                "alex@gmail.com",
                21
        );

        Customer jamila = new Customer(
                2,
                "Jamila",
                "jamila@gmail.com",
                19
        );
        customers = List.of(alex,jamila);
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customers.stream().toList();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        Optional<Customer>customerById=customers.stream().filter(c->c.getId()== id).findFirst();
        return customerById;
    }

    @Override
    public void insertCustomer(Customer customer) {
         customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream().anyMatch(c ->c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        Optional<Customer> checkId = selectCustomerById(id);
        return checkId.isPresent();
    }


}

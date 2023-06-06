package com.spring.fullstack.customer;


import com.spring.fullstack.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private  CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        //Given
        Customer customer = new Customer(
          FAKER.name().fullName(),
         FAKER.internet().safeEmailAddress() +"-"+ UUID.randomUUID(),
         20
        );
        underTest.insertCustomer(customer);
        //When
        List<Customer> actual = underTest.selectAllCustomers();
        //Then
        assertThat(actual).isNotEmpty();

    }

    @Test
    void selectCustomerById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);
        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId().equals(id));
            assertThat(c.getName().equals(customer.getName()));
            assertThat(c.getEmail().equals(customer.getEmail()));
            assertThat(c.getAge().equals(customer.getAge()));
        });
    }
    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        //GIVEN
        int id = -1;
        //WHEN
        var actual = underTest.selectCustomerById(id);
        //THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        //given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers().stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);
        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId().equals(id));
            assertThat(c.getName().equals(customer.getName()));
            assertThat(c.getEmail().equals(customer.getEmail()));
            assertThat(c.getAge().equals(customer.getAge()));
        });
    }

    @Test
    void existsPersonWithEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
       boolean existEmail= underTest.existsCustomerWithEmail(email);
       assertThat(existEmail).isTrue();

    }

    @Test
    void deleteCustomerById() {
        //given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        //when
        List<Customer> actual = underTest.selectAllCustomers();
        int id = actual.stream().filter(c-> c.getEmail().equals(email)).map(Customer::getId).findFirst().orElseThrow();
        underTest.deleteCustomerById(id);
        //then
        boolean checkId = underTest.existsPersonWithId(id);
        assertThat(checkId).isFalse();

    }

    @Test
    void updateCustomer() {
        //given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        //when
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        Customer update = new Customer();
        update.setId(id);
        var newName= "Thays";
        update.setName(newName);
        underTest.updateCustomer(update);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(update.getName()); // change
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });

    }

    @Test
    void existsPersonWithId() {
        //given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        //when
       List<Customer> actual = underTest.selectAllCustomers();
       int id = actual.stream().filter(c-> c.getEmail().equals(email)).map(Customer::getId).findFirst().orElseThrow();
       boolean checkId = underTest.existsPersonWithId(id);
       //then
       assertThat(checkId).isTrue();
    }

}
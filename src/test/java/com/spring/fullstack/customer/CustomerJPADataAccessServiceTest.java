package com.spring.fullstack.customer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest  {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    protected static final Faker FAKER = new Faker();

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();
        //then
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 1;

        // When
        underTest.selectCustomerById(id);

        // Then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(1,
                FAKER.name().fullName(),
                email,
                20
        );
        //when
        underTest.insertCustomer(customer);
        //then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        //when
        underTest.existsCustomerWithEmail(email);
        //then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void deleteCustomerById() {
        //Given
        int id= 1;
        //when
        underTest.deleteCustomerById(id);
        //then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(1,
                FAKER.name().fullName(),
                email,
                20
        );
        //then
        underTest.updateCustomer(customer);
        //when
        verify(customerRepository).save(customer);

    }

    @Test
    void existsPersonWithId() {
        //given
        int id= 1;
        //when
        underTest.existsPersonWithId(id);
        //then
        verify(customerRepository).existsById(id);
    }
}
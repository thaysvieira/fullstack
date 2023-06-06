package com.spring.fullstack.customer;

public record CustomerRegistrationRequest(
                                          String name,
                                          String email,
                                          Integer age) {
}

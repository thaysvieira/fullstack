package com.spring.fullstack.customer;

public record CustomerUpdateRequest( String name,
                                     String email,
                                     Integer age) {
}

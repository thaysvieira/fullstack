package com.spring.fullstack;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.spring.fullstack.customer.Customer;
import com.spring.fullstack.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class FullstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullstackApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository) {

		return args -> {
			Customer alex = new Customer(
					"Alex",
					"alex@gmail.com",
					21);
					var faker = new Faker();
			Random random = new Random();
			Name name = faker.name();
			String firstName = name.firstName();
			String lastName = name.lastName();
			Customer customer = new Customer(
					firstName +  " " + lastName,
					firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
					random.nextInt(16, 99)
			);

			Customer jamila = new Customer(
					"Jamila",
					"jamila@gmail.com",
					19
			);

			List<Customer> customers = List.of(alex, jamila);
			// customerRepository.saveAll(customers);
			customerRepository.save(customer);
		};
	}
}

package com.example.bootcamp.sales.customer;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	Optional<Customer> findByCode(String code); //optional means it could not bring anything back
	
	//Optional<Customer> findByLogin(String userName, String passWord);
}

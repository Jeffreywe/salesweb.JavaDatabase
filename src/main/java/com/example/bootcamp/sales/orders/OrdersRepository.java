package com.example.bootcamp.sales.orders;

import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {
	
	Iterable<Orders> findByStatus(String status);
}

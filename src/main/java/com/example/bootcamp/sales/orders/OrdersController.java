package com.example.bootcamp.sales.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

	@Autowired
	private OrdersRepository ordRepo;
	
	// custom methods
	
	@SuppressWarnings("rawtypes")//responseentity gets a generic list, if we dont put anything in angle brackets, we need to use this annotation
	@PutMapping("review/{id}")//
	public ResponseEntity reviewOrder(@PathVariable int id, @RequestBody Orders order) {
		var statusValue = (order.getTotal() <= 50) ? "APPROVED" : "REVIEW";
		order.setStatus(statusValue);
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approved/{id}")
	public ResponseEntity approvedOrder(@PathVariable int id, @RequestBody Orders order) {
		order.setStatus("APPROVED");
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("rejected/{id}")
	public ResponseEntity rejectedOrder(@PathVariable int id, @RequestBody Orders order) {
		order.setStatus("REJECTED");
		return putOrder(id, order);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("reval/{id}")
	public ResponseEntity reevaluateOrder(@PathVariable int id, @RequestBody Orders order) {
		order.setStatus("REEVALUATE");
		return putOrder(id, order);
	}
	
	@GetMapping("reviews")
	public ResponseEntity<Iterable<Orders>> getOrdersInReview() {
		var orders = ordRepo.findByStatus("REVIEW");
		return new ResponseEntity<Iterable<Orders>>(orders, HttpStatus.OK);
	}
	
	
	
	
	// basic 5 methods
	
	@GetMapping
	public ResponseEntity<Iterable<Orders>> getOrders() {
		var orders = ordRepo.findAll();
		return new ResponseEntity<Iterable<Orders>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Orders> getOrder(@PathVariable int id) {
		var orderd = ordRepo.findById(id);
		if(orderd.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Orders>(orderd.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Orders> postOrder(@RequestBody Orders order) {
		if(order == null || order.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		order.setStatus("NEW");
		var ord = ordRepo.save(order);
		return new ResponseEntity<Orders>(ord, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putOrder(@PathVariable int id, @RequestBody Orders orders) {
		if(orders == null || orders.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var ord = ordRepo.findById(orders.getId());
		if(ord.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ordRepo.save(orders);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteOrder(@PathVariable int id) {
		var ord = ordRepo.findById(id);
		if(ord.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ordRepo.delete(ord.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

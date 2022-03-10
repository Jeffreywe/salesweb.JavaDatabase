package com.example.bootcamp.sales.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bootcamp.sales.orders.Orders;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository custRepo;
	
	// custom methods
	
	@GetMapping("code/{code}")
	public ResponseEntity<Customer> getCustomerByCode(@PathVariable String code) {
		var cust = custRepo.findByCode(code);
		if(cust.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(cust.get(), HttpStatus.OK);
	}
	
	/*@GetMapping("{userName}/{password}")
	public ResponseEntity<Customer> getCustomerByLogin(@PathVariable String userName, String passWord) {
		var cust = custRepo.findByLogin(userName, passWord);
		if(cust.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(cust.get(), HttpStatus.OK);
	}*/
	
	// basic 5 methods
	
	@GetMapping
	public ResponseEntity<Iterable<Customer>> getCustomers() {
		var customers = custRepo.findAll();
		return new ResponseEntity<Iterable<Customer>>(customers, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
		var customer = custRepo.findById(id);
		if(customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);		
	}
	
	@PostMapping
	public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) {
		if(customer == null || customer.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var cust = custRepo.save(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putCustomer(@PathVariable int id, @RequestBody Customer customer) {
		if(customer == null || customer.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var cust = custRepo.findById(customer.getId());
		if(cust.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		custRepo.save(customer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
 	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteCustomer(@PathVariable int id) {
		var customer = custRepo.findById(id);
		if(customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		custRepo.delete(customer.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

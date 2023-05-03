package com.cts.project.bookkeeping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.project.bookkeeping.entities.Customers;
import com.cts.project.bookkeeping.exception.CustomersNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.CustomerModel;
import com.cts.project.bookkeeping.service.CustomersService;

import jakarta.validation.Valid;

@RestController
public class CustomersController {

	@Autowired
	CustomersService cService;
	
	@PostMapping(value="/create/customer")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerModel customer) throws UserNotFoundException{
		Customers cust = cService.createCustomer(customer);
		return new ResponseEntity<>(cust,HttpStatus.OK);
	}
	
	@GetMapping(value = "/get/user/{userId}/customers")
	public List<Customers> getCustomersOfUser(@PathVariable("userId") String userId)throws UserNotFoundException{
		return cService.getCustomersOfUser(userId);
	}
	
	@GetMapping(value = "/get/user/{userId}/customersbyid/{custId}")
	public Customers getCustomerById(@PathVariable("custId") String custId, @PathVariable("userId") String userId) throws CustomersNotFoundException{
		return cService.getCustomerById(custId, userId);
	}
	
	@GetMapping(value = "/get/user/{userId}/customersbyname/{name}")
	public Customers getCustomerByName(@PathVariable("name") String name,@PathVariable("userId") String userId) throws CustomersNotFoundException{
		return cService.getCustomerByName(name, userId);
	}
	
	@PutMapping(value = "/update/user/{userId}/customers/{custId}")
	public ResponseEntity<Object> updateCustomerDetails(@Valid @RequestBody CustomerModel customer, @PathVariable("custId") String custId, @PathVariable("userId") String userId) throws CustomersNotFoundException{
		Customers cust =cService.updateCustomerDetails(customer, custId, userId);
		return new ResponseEntity<>(cust,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/user/{userId}/customers/{custId}")
	public String deleteCustomersById(@PathVariable("custId") String custId, @PathVariable("userId") String userId) throws CustomersNotFoundException{
		return cService.deleteCustomersById(custId, userId);
		
	}
	
}

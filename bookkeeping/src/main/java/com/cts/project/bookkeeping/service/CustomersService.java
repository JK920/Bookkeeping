package com.cts.project.bookkeeping.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.project.bookkeeping.entities.Customers;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.exception.CustomersNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.CustomerModel;
import com.cts.project.bookkeeping.repository.CustomersRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomersService {

	@Autowired
	CustomersRepository cRepo;
	@Autowired
	UsersService uService;

	
	@Transactional
	public Customers createCustomer(CustomerModel customer) throws UserNotFoundException{
		Customers cust = null;

		Users user = uService.getUserById(customer.getUserId());
		cust = new Customers(user, customer.getName(),customer.getAddress(),customer.getPhoneNo(),customer.getEmail());
		cust = cRepo.save(cust);

		return cust;
	}

	
	@Transactional
	public List<Customers> getCustomersOfUser(String userId)throws UserNotFoundException{
	
		Users user = uService.getUserById(userId);
		return user.getCustomerList();

	}
	
	@Transactional
	public Customers getCustomerById(String custId, String userId) throws CustomersNotFoundException{
		List<Customers> customerList = getCustomersOfUser(userId);
		Customers customer = null;
		for(Customers c:customerList) {
			if(c.getCustomerId().equals(custId)) {
				customer = c;
			}
		}
		if(customer!=null) {
			return customer;
		}
		else {
			throw new CustomersNotFoundException("Customer Not Found!!");
		}
	}
	
	@Transactional
	public Customers getCustomerByName(String name, String userId) throws CustomersNotFoundException{
		List<Customers> customerList = getCustomersOfUser(userId);
		Customers customer = null;
		for(Customers c:customerList) {
			if(c.getName().equals(name)) {
				customer = c;
			}
		}
		if(customer!=null) {
			return customer;
		}
		else {
			throw new CustomersNotFoundException("Customer Not Found!!");
		}
	}
	
	
	@Transactional
	public Customers updateCustomerDetails(CustomerModel customer, String id) throws CustomersNotFoundException{
		Customers cust = null;
	
		cust = getCustomerById(id, customer.getUserId());
		cust.setName(customer.getName());
		cust.setEmail(customer.getEmail());
		cust.setPhoneNo(customer.getPhoneNo());
		cust.setAddress(customer.getAddress());
		cust = cRepo.save(cust);

		return cust;
	}
	
	
	@Transactional
	public String deleteCustomersById(String custId, String userId) throws CustomersNotFoundException{
		String res = "";
		Customers c = getCustomerById(custId, userId);
		String name = c.getName();
		if(c.getJournalList()==null) {
			cRepo.delete(c);
			res = "Customer "+name+" deleted";
		}
		else {
			res = "Customer is associated with journals";
		}
		return res;
		
	}
}

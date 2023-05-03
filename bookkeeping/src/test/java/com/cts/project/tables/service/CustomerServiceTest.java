package com.cts.project.tables.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.cts.project.bookkeeping.TablesApplication;
import com.cts.project.bookkeeping.entities.Customers;
import com.cts.project.bookkeeping.entities.Journal;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.exception.CustomersNotFoundException;
import com.cts.project.bookkeeping.exception.UserNotFoundException;
import com.cts.project.bookkeeping.model.CustomerModel;
import com.cts.project.bookkeeping.repository.CustomersRepository;
import com.cts.project.bookkeeping.service.CustomersService;
import com.cts.project.bookkeeping.service.UsersService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@DirtiesContext
class CustomerServiceTest {

	@Mock
	CustomersRepository cR;

	@Mock
	UsersService uS;
	
	@InjectMocks
	CustomersService cS;
	
	private Users user;
	private CustomerModel cm;
	private Customers c;
	private Customers c1;
	private Customers c2;
	private List<Customers> cList = new ArrayList<>();
	Journal j;
	
	@BeforeEach
	public void setup() {
		user = new Users("John", "john123", "password", "john@gmail.com", "New Company");
		user.setUserId("u1");
		cm = new CustomerModel("u1", "Sam", "Cochin", "987654321","sam@email.com");
		c = new Customers(user,cm.getName(),cm.getAddress(),cm.getPhoneNo(),cm.getEmail());
		c.setCustomerId("c");
		c1 = new Customers(user,"customer1", "address1", "9988776655", "c1@email.com");
		c1.setCustomerId("c1");
		c2 = new Customers(user,"customer2", "address2", "9988776655", "c2@email.com");
		c2.setCustomerId("c2");
		cList = List.of(c,c1,c2);
		user.setCustomerList(cList);
		j= new Journal();
	}
	
	@Test
	 void testCreateCustomer() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenReturn(user);
		when(cR.save(any(Customers.class))).thenReturn(c);
		Customers customer = cS.createCustomer(cm);
		assertEquals(c, customer);
		
	}
	
	@Test
	 void testCreateCustomer_UserNotFound() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, ()-> cS.createCustomer(cm));
		
	}
	
	@Test
	 void testGetCustomersOfUsers() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenReturn(user);
		assertIterableEquals(cList, cS.getCustomersOfUser("u1"));
	}
	
	@Test
	 void testGetCustomersOfUsers_UserNotFound() throws UserNotFoundException{
		
		when(uS.getUserById("u1")).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class,()-> cS.getCustomersOfUser("u1"));
	}
	
	@Test
	void testGetCustomerById()throws CustomersNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals(c1, cS.getCustomerById("c1", "u1"));
	}
	@Test
	void testGetCustomerById_CustomerNotFound()throws CustomersNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(CustomersNotFoundException.class,()-> cS.getCustomerById("c0", "u1"));
	}
	
	@Test
	void testGetCustomerByName()throws CustomersNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals(c1, cS.getCustomerByName("customer1", "u1"));
	}
	
	@Test
	void testGetCustomerByName_CustomerNotFound()throws CustomersNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(CustomersNotFoundException.class,()-> cS.getCustomerByName("customer", "u1"));
	}
	
	@Test
	void testUpdateCustomerDetails() throws CustomersNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		when(cR.save(any(Customers.class))).thenReturn(c);
		assertEquals(c, cS.updateCustomerDetails(cm, "c", "u1"));
	}
	
	@Test
	void testUpdateCustomerDetails_CustomerNotFound() throws CustomersNotFoundException {
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(CustomersNotFoundException.class,()-> cS.updateCustomerDetails(cm, "c3","u1"));
	}
	
	@Test
	void testDeleteCustomersById_success() throws CustomersNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals("Customer Sam deleted", cS.deleteCustomersById("c", "u1"));
	}
	
	@Test
	void testDeleteCustomersById_failed() throws CustomersNotFoundException{
		c.setJournalList(List.of(j));
		when(uS.getUserById("u1")).thenReturn(user);
		assertEquals("Customer is associated with journals", cS.deleteCustomersById("c", "u1"));
	}
	
	@Test
	void testDeleteCustomersById_CustomerNotFound() throws CustomersNotFoundException{
		when(uS.getUserById("u1")).thenReturn(user);
		assertThrows(CustomersNotFoundException.class,()-> cS.deleteCustomersById("c3", "u1"));
	}
	
}

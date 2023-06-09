package com.cts.project.tables.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.project.bookkeeping.TablesApplication;
import com.cts.project.bookkeeping.controller.CustomersController;
import com.cts.project.bookkeeping.entities.Customers;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.model.CustomerModel;
import com.cts.project.bookkeeping.service.CustomersService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	CustomersService cS;
	
	@InjectMocks
	CustomersController cC;
	
	private CustomerModel cm;
	private Customers c;
	
	@BeforeEach
	void setUp() throws Exception {
		cm = new CustomerModel("u1", "customer", "address", "9876543210", "customer@email.com");
		c = new Customers(new Users(), cm.getName(), cm.getAddress(), cm.getPhoneNo(), cm.getEmail());
	}

	@Test
	void testCreateCustomer() throws Exception {
		String exp = objectMapper.writeValueAsString(c);
		when(cS.createCustomer(any(CustomerModel.class))).thenReturn(c);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/create/customer")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cm))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetCustomersOfUsers() throws Exception {
		String exp = objectMapper.writeValueAsString(List.of(c));
		when(cS.getCustomersOfUser("u1")).thenReturn(List.of(c));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/customers")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetCustomerById() throws Exception {
		String exp = objectMapper.writeValueAsString(c);
		when(cS.getCustomerById("c1","u1")).thenReturn(c);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/customersbyid/c1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}

	@Test
	void testGetCustomersByName() throws Exception {
		String exp = objectMapper.writeValueAsString(c);
		when(cS.getCustomerByName("customer","u1")).thenReturn(c);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/customersbyname/customer")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	
	@Test
	void testUpdateCustomers() throws Exception {
		String exp = objectMapper.writeValueAsString(c);
		when(cS.updateCustomerDetails(any(CustomerModel.class), anyString())).thenReturn(c);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/user/customers/c1")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cm))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testDeleteCustomers() throws Exception {
		String exp ="Customer Deleted";
		when(cS.deleteCustomersById(anyString(), anyString())).thenReturn(exp);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/delete/user/u1/customers/c1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
}

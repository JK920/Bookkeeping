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
import com.cts.project.bookkeeping.controller.VendorsController;
import com.cts.project.bookkeeping.entities.Vendors;
import com.cts.project.bookkeeping.entities.Users;
import com.cts.project.bookkeeping.model.VendorModel;
import com.cts.project.bookkeeping.service.VendorsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TablesApplication.class)
@AutoConfigureMockMvc
class VendorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	VendorsService vS;
	
	@InjectMocks
	VendorsController vC;
	
	private VendorModel vm;
	private Vendors v;
	
	@BeforeEach
	void setUp() throws Exception {
		vm = new VendorModel("u1", "vendor", "address", "9876543210", "vendor@email.com");
		v = new Vendors(new Users(), vm.getName(), vm.getAddress(), vm.getPhoneNo(), vm.getEmail());
	}

	@Test
	void testCreateVendor() throws Exception {
		String exp = objectMapper.writeValueAsString(v);
		when(vS.createVendor(any(VendorModel.class))).thenReturn(v);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/create/vendor")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(vm))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetVendorsOfUsers() throws Exception {
		String exp = objectMapper.writeValueAsString(List.of(v));
		when(vS.getVendorsOfUser("u1")).thenReturn(List.of(v));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/vendors")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testGetVendorById() throws Exception {
		String exp = objectMapper.writeValueAsString(v);
		when(vS.getVendorById("c1","u1")).thenReturn(v);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/vendorsbyid/c1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}

	@Test
	void testGetVendorsByName() throws Exception {
		String exp = objectMapper.writeValueAsString(v);
		when(vS.getVendorByName("vendor","u1")).thenReturn(v);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/get/user/u1/vendorsbyname/vendor")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	
	@Test
	void testUpdateVendors() throws Exception {
		String exp = objectMapper.writeValueAsString(v);
		when(vS.updateVendorDetails(any(VendorModel.class), anyString())).thenReturn(v);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/update/user/vendors/c1")
				.accept(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(vm))
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
	
	@Test
	void testDeleteVendors() throws Exception {
		String exp ="Vendor Deleted";
		when(vS.deleteVendorsById(anyString(), anyString())).thenReturn(exp);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/delete/user/u1/vendors/c1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		assertEquals(200, response.getStatus());
		assertEquals(exp, actual);
	}
}
